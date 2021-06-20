package com.example.airline_explorer

import com.example.airline_explorer.data.model.Airline
import com.example.airline_explorer.data.source.AirlinesRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AirlinesRepositoryTest {

    private lateinit var airlinesRepository: AirlinesRepository

    @Before
    fun setupAirlinesRepository() {

        airlinesRepository = AirlinesRepository(
            airlinesRemoteDataSource = MockedAirlineRemoteDataSource(),
            networkConnectivityChecker = MockedNetworkConnectivityCheckerImpl()
        )
    }

    @Test
    fun testAirlinesRepository_fetchAirlinesData() {
        val airlinesTestSingle = airlinesRepository.fetchAirlinesData()

        val testAirlines: List<Airline> = airlinesTestSingle.blockingGet()

        assertEquals(airline.name, testAirlines[0].name)
    }

    @Test
    fun testAirlinesRepository_getAirline() {
        val airlineTestSingle = airlinesRepository.getAirline(airline.id!!)

        val testAirline: Airline = airlineTestSingle.blockingGet()

        assertEquals(airline.name, testAirline.name)
    }

    companion object {
        val airline = Airline(
            "testid", "testname", "testlogo", "testslogan", "testestablished",
            "testcountry", "testwebsite", "testheadquarters"
        )
    }
}