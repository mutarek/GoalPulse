package com.prosolution.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

interface WorldCupApi {
    @GET("matches")
    suspend fun getMatches(
        @Query("day") day: String? = null,
        @Query("status") status: String? = null
    ): MatchesResponse

    @GET("standings")
    suspend fun getStandings(@Query("group") group: String? = null): StandingsResponse
}

@Serializable
data class MatchesResponse(
    @SerialName("matches") val matches: List<MatchDto>
)

@Serializable
data class StandingsResponse(
    @SerialName("standings") val standings: List<StandingDto>
)

@Serializable
data class MatchDto(
    val id: String,
    @SerialName("home_team") val homeTeam: TeamDto,
    @SerialName("away_team") val awayTeam: TeamDto,
    @SerialName("start_time_utc") val startTimeUtc: String,
    val stadium: String,
    @SerialName("home_score") val homeScore: Int,
    @SerialName("away_score") val awayScore: Int,
    val minute: Int,
    val status: String
)

@Serializable
data class TeamDto(
    val id: String,
    val name: String,
    @SerialName("short_code") val shortCode: String,
    @SerialName("crest_url") val crestUrl: String,
    val group: String
)

@Serializable
data class StandingDto(
    val team: TeamDto,
    val played: Int,
    val won: Int,
    val draw: Int,
    val lost: Int,
    @SerialName("goals_for") val goalsFor: Int,
    @SerialName("goals_against") val goalsAgainst: Int,
    val points: Int
)

