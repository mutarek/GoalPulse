package com.prosolution.core.data

import com.prosolution.core.domain.model.Match
import com.prosolution.core.domain.model.MatchStatus
import com.prosolution.core.domain.model.Standing
import com.prosolution.core.domain.model.Team

object StaticWorldCupData {

    // ─── Teams ───────────────────────────────────────────────────────────────
    // Group A
    private val BRA = team("bra", "Brazil",      "BRA", "A", "🇧🇷")
    private val JPN = team("jpn", "Japan",       "JPN", "A", "🇯🇵")
    private val KOR = team("kor", "South Korea", "KOR", "A", "🇰🇷")
    private val CRC = team("crc", "Costa Rica",  "CRC", "A", "🇨🇷")

    // Group B
    private val ARG = team("arg", "Argentina",   "ARG", "B", "🇦🇷")
    private val FRA = team("fra", "France",      "FRA", "B", "🇫🇷")
    private val MEX = team("mex", "Mexico",      "MEX", "B", "🇲🇽")
    private val CAN = team("can", "Canada",      "CAN", "B", "🇨🇦")

    // Group C
    private val ESP = team("esp", "Spain",       "ESP", "C", "🇪🇸")
    private val POR = team("por", "Portugal",    "POR", "C", "🇵🇹")
    private val URU = team("uru", "Uruguay",     "URU", "C", "🇺🇾")
    private val SUI = team("sui", "Switzerland", "SUI", "C", "🇨🇭")

    // Group D
    private val ENG = team("eng", "England",     "ENG", "D", "🏴󠁧󠁢󠁥󠁮󠁧󠁿")
    private val GER = team("ger", "Germany",     "GER", "D", "🇩🇪")
    private val NED = team("ned", "Netherlands", "NED", "D", "🇳🇱")
    private val BEL = team("bel", "Belgium",     "BEL", "D", "🇧🇪")

    // Group E
    private val USA = team("usa", "USA",         "USA", "E", "🇺🇸")
    private val ITA = team("ita", "Italy",       "ITA", "E", "🇮🇹")
    private val COL = team("col", "Colombia",    "COL", "E", "🇨🇴")
    private val ECU = team("ecu", "Ecuador",     "ECU", "E", "🇪🇨")

    // Group F
    private val MAR = team("mar", "Morocco",     "MAR", "F", "🇲🇦")
    private val SEN = team("sen", "Senegal",     "SEN", "F", "🇸🇳")
    private val NGA = team("nga", "Nigeria",     "NGA", "F", "🇳🇬")
    private val ALG = team("alg", "Algeria",     "ALG", "F", "🇩🇿")

    // Group G
    private val HRV = team("hrv", "Croatia",    "CRO", "G", "🇭🇷")
    private val TUR = team("tur", "Turkey",      "TUR", "G", "🇹🇷")
    private val AUS = team("aus", "Australia",   "AUS", "G", "🇦🇺")
    private val PER = team("per", "Peru",        "PER", "G", "🇵🇪")

    // Group H
    private val DEN = team("den", "Denmark",     "DEN", "H", "🇩🇰")
    private val AUT = team("aut", "Austria",     "AUT", "H", "🇦🇹")
    private val CHL = team("chl", "Chile",       "CHL", "H", "🇨🇱")
    private val PAN = team("pan", "Panama",      "PAN", "H", "🇵🇦")

    // ─── Matches ──────────────────────────────────────────────────────────────
    val matches: List<Match> = listOf(

        // ── GROUP A ──────────────────────────────────────────────────
        // Matchday 1
        match("a01", BRA, JPN, "2026-06-11T18:00:00Z", "MetLife Stadium, New York",        3, 1, 90, MatchStatus.FINISHED),
        match("a02", KOR, CRC, "2026-06-11T22:00:00Z", "AT&T Stadium, Dallas",              2, 0, 90, MatchStatus.FINISHED),
        // Matchday 2
        match("a03", BRA, KOR, "2026-06-19T18:00:00Z", "Rose Bowl, Los Angeles",            0, 0, 0,  MatchStatus.UPCOMING),
        match("a04", JPN, CRC, "2026-06-19T22:00:00Z", "MetLife Stadium, New York",         0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 3 (simultaneous)
        match("a05", BRA, CRC, "2026-06-27T18:00:00Z", "AT&T Stadium, Dallas",              0, 0, 0,  MatchStatus.UPCOMING),
        match("a06", JPN, KOR, "2026-06-27T18:00:00Z", "Rose Bowl, Los Angeles",            0, 0, 0,  MatchStatus.UPCOMING),

        // ── GROUP B ──────────────────────────────────────────────────
        // Matchday 1
        match("b01", ARG, FRA, "2026-06-12T18:00:00Z", "SoFi Stadium, Los Angeles",        2, 1, 90, MatchStatus.FINISHED),
        match("b02", MEX, CAN, "2026-06-12T22:00:00Z", "Estadio Azteca, Mexico City",       1, 2, 90, MatchStatus.FINISHED),
        // Matchday 2
        match("b03", ARG, MEX, "2026-06-20T18:00:00Z", "Estadio Azteca, Mexico City",       0, 0, 0,  MatchStatus.UPCOMING),
        match("b04", FRA, CAN, "2026-06-20T22:00:00Z", "Gillette Stadium, Boston",          0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 3 (simultaneous)
        match("b05", ARG, CAN, "2026-06-28T18:00:00Z", "MetLife Stadium, New York",         0, 0, 0,  MatchStatus.UPCOMING),
        match("b06", FRA, MEX, "2026-06-28T18:00:00Z", "Estadio Azteca, Mexico City",       0, 0, 0,  MatchStatus.UPCOMING),

        // ── GROUP C ──────────────────────────────────────────────────
        // Matchday 1
        match("c01", ESP, POR, "2026-06-13T18:00:00Z", "Rose Bowl, Los Angeles",            0, 0, 90, MatchStatus.FINISHED),
        match("c02", URU, SUI, "2026-06-13T22:00:00Z", "Hard Rock Stadium, Miami",          1, 0, 90, MatchStatus.FINISHED),
        // Matchday 2
        match("c03", ESP, URU, "2026-06-21T18:00:00Z", "Hard Rock Stadium, Miami",          0, 0, 0,  MatchStatus.UPCOMING),
        match("c04", POR, SUI, "2026-06-21T22:00:00Z", "AT&T Stadium, Dallas",              0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 3 (simultaneous)
        match("c05", ESP, SUI, "2026-06-29T18:00:00Z", "Mercedes-Benz Stadium, Atlanta",    0, 0, 0,  MatchStatus.UPCOMING),
        match("c06", POR, URU, "2026-06-29T18:00:00Z", "Gillette Stadium, Boston",          0, 0, 0,  MatchStatus.UPCOMING),

        // ── GROUP D ──────────────────────────────────────────────────
        // Matchday 1 — LIVE
        match("d01", ENG, GER, "2026-06-14T18:00:00Z", "Lumen Field, Seattle",              0, 0, 38, MatchStatus.LIVE),
        match("d02", NED, BEL, "2026-06-14T22:00:00Z", "NRG Stadium, Houston",              0, 1, 54, MatchStatus.LIVE),
        // Matchday 2
        match("d03", ENG, NED, "2026-06-22T18:00:00Z", "Lumen Field, Seattle",              0, 0, 0,  MatchStatus.UPCOMING),
        match("d04", GER, BEL, "2026-06-22T22:00:00Z", "NRG Stadium, Houston",              0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 3 (simultaneous)
        match("d05", ENG, BEL, "2026-06-30T18:00:00Z", "Lumen Field, Seattle",              0, 0, 0,  MatchStatus.UPCOMING),
        match("d06", GER, NED, "2026-06-30T18:00:00Z", "NRG Stadium, Houston",              0, 0, 0,  MatchStatus.UPCOMING),

        // ── GROUP E ──────────────────────────────────────────────────
        // Matchday 1
        match("e01", USA, ITA, "2026-06-15T18:00:00Z", "MetLife Stadium, New York",         0, 0, 0,  MatchStatus.UPCOMING),
        match("e02", COL, ECU, "2026-06-15T22:00:00Z", "Mercedes-Benz Stadium, Atlanta",    0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 2
        match("e03", USA, COL, "2026-06-23T18:00:00Z", "Bank of America Stadium, Charlotte",0, 0, 0,  MatchStatus.UPCOMING),
        match("e04", ITA, ECU, "2026-06-23T22:00:00Z", "Rose Bowl, Los Angeles",            0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 3 (simultaneous)
        match("e05", USA, ECU, "2026-07-01T18:00:00Z", "SoFi Stadium, Los Angeles",         0, 0, 0,  MatchStatus.UPCOMING),
        match("e06", ITA, COL, "2026-07-01T18:00:00Z", "MetLife Stadium, New York",         0, 0, 0,  MatchStatus.UPCOMING),

        // ── GROUP F ──────────────────────────────────────────────────
        // Matchday 1
        match("f01", MAR, SEN, "2026-06-16T18:00:00Z", "AT&T Stadium, Dallas",              0, 0, 0,  MatchStatus.UPCOMING),
        match("f02", NGA, ALG, "2026-06-16T22:00:00Z", "Gillette Stadium, Boston",          0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 2
        match("f03", MAR, NGA, "2026-06-24T18:00:00Z", "BMO Field, Toronto",                0, 0, 0,  MatchStatus.UPCOMING),
        match("f04", SEN, ALG, "2026-06-24T22:00:00Z", "Estadio Azteca, Mexico City",       0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 3 (simultaneous)
        match("f05", MAR, ALG, "2026-07-02T18:00:00Z", "BMO Field, Toronto",                0, 0, 0,  MatchStatus.UPCOMING),
        match("f06", SEN, NGA, "2026-07-02T18:00:00Z", "Hard Rock Stadium, Miami",          0, 0, 0,  MatchStatus.UPCOMING),

        // ── GROUP G ──────────────────────────────────────────────────
        // Matchday 1
        match("g01", HRV, TUR, "2026-06-17T18:00:00Z", "BMO Field, Toronto",                0, 0, 0,  MatchStatus.UPCOMING),
        match("g02", AUS, PER, "2026-06-17T22:00:00Z", "Rose Bowl, Los Angeles",            0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 2
        match("g03", HRV, AUS, "2026-06-25T18:00:00Z", "MetLife Stadium, New York",         0, 0, 0,  MatchStatus.UPCOMING),
        match("g04", TUR, PER, "2026-06-25T22:00:00Z", "SoFi Stadium, Los Angeles",         0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 3 (simultaneous)
        match("g05", HRV, PER, "2026-07-03T18:00:00Z", "AT&T Stadium, Dallas",              0, 0, 0,  MatchStatus.UPCOMING),
        match("g06", TUR, AUS, "2026-07-03T18:00:00Z", "MetLife Stadium, New York",         0, 0, 0,  MatchStatus.UPCOMING),

        // ── GROUP H ──────────────────────────────────────────────────
        // Matchday 1
        match("h01", DEN, AUT, "2026-06-18T18:00:00Z", "SoFi Stadium, Los Angeles",         0, 0, 0,  MatchStatus.UPCOMING),
        match("h02", CHL, PAN, "2026-06-18T22:00:00Z", "Estadio Azteca, Mexico City",       0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 2
        match("h03", DEN, CHL, "2026-06-26T18:00:00Z", "Hard Rock Stadium, Miami",          0, 0, 0,  MatchStatus.UPCOMING),
        match("h04", AUT, PAN, "2026-06-26T22:00:00Z", "AT&T Stadium, Dallas",              0, 0, 0,  MatchStatus.UPCOMING),
        // Matchday 3 (simultaneous)
        match("h05", DEN, PAN, "2026-07-04T18:00:00Z", "Rose Bowl, Los Angeles",            0, 0, 0,  MatchStatus.UPCOMING),
        match("h06", AUT, CHL, "2026-07-04T18:00:00Z", "NRG Stadium, Houston",              0, 0, 0,  MatchStatus.UPCOMING)
    )

    // ─── Standings (after Matchday 1 results) ─────────────────────────────────
    val standings: List<Standing> = listOf(
        // Group A
        standing(BRA, 1, 1, 0, 0, 3, 1, 3),
        standing(KOR, 1, 1, 0, 0, 2, 0, 3),
        standing(JPN, 1, 0, 0, 1, 1, 3, 0),
        standing(CRC, 1, 0, 0, 1, 0, 2, 0),
        // Group B
        standing(ARG, 1, 1, 0, 0, 2, 1, 3),
        standing(CAN, 1, 1, 0, 0, 2, 1, 3),
        standing(FRA, 1, 0, 0, 1, 1, 2, 0),
        standing(MEX, 1, 0, 0, 1, 1, 2, 0),
        // Group C
        standing(URU, 1, 1, 0, 0, 1, 0, 3),
        standing(ESP, 1, 0, 1, 0, 0, 0, 1),
        standing(POR, 1, 0, 1, 0, 0, 0, 1),
        standing(SUI, 1, 0, 0, 1, 0, 1, 0),
        // Group D – still live, no final standings
        standing(ENG, 0, 0, 0, 0, 0, 0, 0),
        standing(GER, 0, 0, 0, 0, 0, 0, 0),
        standing(NED, 0, 0, 0, 0, 0, 0, 0),
        standing(BEL, 0, 0, 0, 0, 0, 0, 0),
        // Groups E-H – not yet played
        standing(USA, 0, 0, 0, 0, 0, 0, 0),
        standing(ITA, 0, 0, 0, 0, 0, 0, 0),
        standing(COL, 0, 0, 0, 0, 0, 0, 0),
        standing(ECU, 0, 0, 0, 0, 0, 0, 0),
        standing(MAR, 0, 0, 0, 0, 0, 0, 0),
        standing(SEN, 0, 0, 0, 0, 0, 0, 0),
        standing(NGA, 0, 0, 0, 0, 0, 0, 0),
        standing(ALG, 0, 0, 0, 0, 0, 0, 0),
        standing(HRV, 0, 0, 0, 0, 0, 0, 0),
        standing(TUR, 0, 0, 0, 0, 0, 0, 0),
        standing(AUS, 0, 0, 0, 0, 0, 0, 0),
        standing(PER, 0, 0, 0, 0, 0, 0, 0),
        standing(DEN, 0, 0, 0, 0, 0, 0, 0),
        standing(AUT, 0, 0, 0, 0, 0, 0, 0),
        standing(CHL, 0, 0, 0, 0, 0, 0, 0),
        standing(PAN, 0, 0, 0, 0, 0, 0, 0)
    )

    // ─── Helpers ─────────────────────────────────────────────────────────────
    fun scheduleForDay(day: String?): List<Match> =
        if (day.isNullOrBlank()) matches
        else matches.filter { it.startTimeUtc.startsWith(day) }

    fun liveMatches(): List<Match> = matches.filter { it.status == MatchStatus.LIVE }

    private fun team(id: String, name: String, shortCode: String, group: String, flag: String): Team =
        Team(id = id, name = name, shortCode = shortCode, crestUrl = "", group = group, flag = flag)

    private fun match(
        id: String, homeTeam: Team, awayTeam: Team,
        startTimeUtc: String, stadium: String,
        homeScore: Int, awayScore: Int, minute: Int, status: MatchStatus
    ): Match = Match(
        id = id, homeTeam = homeTeam, awayTeam = awayTeam,
        startTimeUtc = startTimeUtc, stadium = stadium,
        homeScore = homeScore, awayScore = awayScore,
        minute = minute, status = status
    )

    private fun standing(
        team: Team, played: Int, won: Int, draw: Int, lost: Int,
        goalsFor: Int, goalsAgainst: Int, points: Int
    ): Standing = Standing(
        team = team, played = played, won = won, draw = draw, lost = lost,
        goalsFor = goalsFor, goalsAgainst = goalsAgainst, points = points
    )
}

