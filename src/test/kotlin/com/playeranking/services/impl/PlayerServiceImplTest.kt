import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import com.playeranking.models.Player
import com.playeranking.services.impl.PlayerServiceImpl
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.bson.BsonDocument
import org.bson.BsonString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class PlayerServiceImplTest {

    private val playerCollection = mockk<CoroutineCollection<Player>>()
    private val database = mockk<CoroutineDatabase>()

    private val playerService = PlayerServiceImpl()

    @Before
    fun setup() {
        clearMocks(playerCollection, database)
        every { database.getCollection<Player>("player") } returns playerCollection
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun testGetAllPlayers() = runBlocking {
        val players = listOf(Player("John", "100"), Player("Alice", "200"))
        coEvery { playerCollection.find().toList() } returns players

        val result = playerService.getAllPlayers()

        assert(result == players)
        coVerify(exactly = 1) { playerCollection.find().toList() }
    }

    @Test
    fun testGetPlayerByPseudo() = runBlocking {
        val pseudo = "John"
        val player = Player("John", "100")
        coEvery { playerCollection.findOne(Player::pseudo eq pseudo) } returns player

        val result = playerService.getPlayerByPseudo(BsonDocument("pseudo", BsonString(pseudo)))

        assert(result == player)
        coVerify(exactly = 1) { playerCollection.findOne(Player::pseudo eq pseudo) }
    }

    @Test
    fun testSavePlayer() = runBlocking {
        val player = Player("John", "100")
        val insertResult = mockk<InsertOneResult>()
        every { insertResult.wasAcknowledged() } returns true
        coEvery { playerCollection.insertOne(player) } returns insertResult

        val result = playerService.savePlayer(player)

        assert(result)
        coVerify(exactly = 1) { playerCollection.insertOne(player) }
    }

    @Test
    fun testReplaceOneByPseudo() = runBlocking {
        val pseudo = "John"
        val updatedPlayer = Player("John", "200")
        val updateResult = mockk<UpdateResult>()
        every { updateResult.wasAcknowledged() } returns true
        coEvery { playerCollection.replaceOne(Player::pseudo eq pseudo, updatedPlayer) } returns updateResult

        val result = playerService.replaceOneByPseudo(pseudo, updatedPlayer)

        assert(result)
        coVerify(exactly = 1) { playerCollection.replaceOne(Player::pseudo eq pseudo, updatedPlayer) }
    }

    @Test
    fun testDeleteAllPlayers() = runBlocking {
        val deleteResult = mockk<DeleteResult>()
        every { deleteResult.wasAcknowledged() } returns true
        coEvery { playerCollection.deleteMany(any<BsonDocument>()) } returns deleteResult

        val result = playerService.deleteAllPlayers()

        assert(result)
        coVerify(exactly = 1) { playerCollection.deleteMany(any<BsonDocument>()) }
    }
}
