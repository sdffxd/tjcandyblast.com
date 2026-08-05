package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BugReportEntity
import com.example.data.CandyDatabase
import com.example.data.UserProgressEntity
import com.example.model.CandyWorld
import com.example.model.DailyQuest
import com.example.model.FaqItem
import com.example.model.GalleryCategory
import com.example.model.GalleryItem
import com.example.model.GameFeature
import com.example.model.LeaderboardUser
import com.example.model.NewsItem
import com.example.ui.components.NavPage
import com.example.ui.theme.CandyBlue
import com.example.ui.theme.CandyCyan
import com.example.ui.theme.CandyLime
import com.example.ui.theme.CandyMagenta
import com.example.ui.theme.CandyOrange
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyPurple
import com.example.ui.theme.CandyYellow
import com.example.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class CandyViewModel(application: Application) : AndroidViewModel(application) {

    private val db = CandyDatabase.getDatabase(application)
    private val bugReportDao = db.bugReportDao()
    private val userProgressDao = db.userProgressDao()

    private val _currentPage = MutableStateFlow(NavPage.HOME)
    val currentPage: StateFlow<NavPage> = _currentPage.asStateFlow()

    private val _userProgress = MutableStateFlow(UserProgressEntity())
    val userProgress: StateFlow<UserProgressEntity> = _userProgress.asStateFlow()

    private val _bugReports = MutableStateFlow<List<BugReportEntity>>(emptyList())
    val bugReports: StateFlow<List<BugReportEntity>> = _bugReports.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    init {
        viewModelScope.launch {
            val progress = userProgressDao.getUserProgress().firstOrNull() ?: UserProgressEntity()
            _userProgress.value = progress

            bugReportDao.getAllReports().collect { list ->
                _bugReports.value = list
            }
        }
    }

    fun selectPage(page: NavPage) {
        _currentPage.value = page
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    fun addGems(amount: Int) {
        viewModelScope.launch {
            val updated = _userProgress.value.copy(gems = _userProgress.value.gems + amount)
            _userProgress.value = updated
            userProgressDao.saveUserProgress(updated)
            _toastMessage.value = "+$amount Gems Claimed!"
        }
    }

    fun updateHighScore(newScore: Int) {
        if (newScore > _userProgress.value.highScore) {
            viewModelScope.launch {
                val updated = _userProgress.value.copy(highScore = newScore)
                _userProgress.value = updated
                userProgressDao.saveUserProgress(updated)
                _toastMessage.value = "New High Score: $newScore!"
            }
        }
    }

    fun submitBugReport(name: String, email: String, category: String, message: String, deviceInfo: String) {
        viewModelScope.launch {
            val report = BugReportEntity(
                timestamp = System.currentTimeMillis(),
                name = name,
                email = email,
                category = category,
                message = message,
                deviceInfo = deviceInfo
            )
            bugReportDao.insertReport(report)
            _toastMessage.value = "Bug report submitted! Support team will reach out to $email."
        }
    }

    // Static Data Providers
    val candyWorlds = listOf(
        CandyWorld("w1", "Aurora Candy Valley", "Pastel candy hills bathed in magical aurora lights with rainbow jelly streams.", "Gummy Aurora Star", 150, R.drawable.img_world_aurora_1785898394274, CandyPink),
        CandyWorld("w2", "Crystal Sugar Caverns", "Sparkling underground caverns filled with glowing rock candy and crystal gems.", "Sugar Crystal Bomb", 180, R.drawable.img_world_crystal_1785898410077, CandyCyan),
        CandyWorld("w3", "Neon Dessert City", "Futuristic cyberpunk candy cityscape with neon licorice skyscrapers and soda fountains.", "Neon Licorice Blast", 200, R.drawable.img_world_neon_1785898423809, CandyMagenta),
        CandyWorld("w4", "Starlight Cookie Galaxy", "Cosmic realm filled with floating chocolate chip cookie planets and milky way frosting.", "Cosmic Cookie Comet", 160, R.drawable.img_candy_hero_1785898379829, CandyYellow),
        CandyWorld("w5", "Bubble Reef", "Underwater ocean of sparkling soda pop, gummy fish, and fizzy candy reefs.", "Fizzy Soda Bubble", 140, R.drawable.img_world_aurora_1785898394274, CandyBlue),
        CandyWorld("w6", "Moonlit Marshmallow Valley", "Soft fluffy marshmallow clouds and gentle chocolate rivers under a crescent moon.", "Marshmallow Moon Cloud", 170, R.drawable.img_world_crystal_1785898410077, CandyPurple),
        CandyWorld("w7", "Prism Candy Temple", "Ancient temple built with rainbow sugar bricks and legendary candy relics.", "Rainbow Prism Blast", 190, R.drawable.img_world_neon_1785898423809, CandyLime),
        CandyWorld("w8", "Dream Jelly Gardens", "Enchanted botanical gardens with blooming lollipop flowers and jelly waterfalls.", "Blooming Jelly Orchid", 220, R.drawable.img_candy_hero_1785898379829, CandyOrange)
    )

    val gameFeatures = listOf(
        GameFeature("f1", "Hundreds of Original Levels", "Over 1,200 handcrafted levels filled with unique mechanics and candy combos.", "Extension", "1200+ Levels"),
        GameFeature("f2", "Daily Rewards", "Log in every day to claim free gems, boosters, and mystery candy gifts.", "CardGiftcard", "Free Daily"),
        GameFeature("f3", "Lucky Spin", "Spin the wheel every 24 hours for instant booster rewards and rare gems.", "Casino", "Interactive"),
        GameFeature("f4", "Battle Pass", "Unlock free and premium tiers of seasonal cosmetics and explosive boosters.", "MilitaryTech", "Season 4"),
        GameFeature("f5", "Daily Quests", "Complete fun daily objectives to earn bonus exp and chest keys.", "AssignmentTurnedIn", "New Today"),
        GameFeature("f6", "Leaderboards", "Compete against global candy blast champions and climb the leaderboards.", "Leaderboard", "Global Ranks"),
        GameFeature("f7", "Clubs", "Form or join Candy Guilds with friends to share lives and club chests.", "Groups", "Social"),
        GameFeature("f8", "Special Events", "Participate in weekend tournaments, boss battles, and seasonal festivals.", "EmojiEvents", "Live Now"),
        GameFeature("f9", "Powerful Boosters", "Unleash Color Bombs, Candy Hammers, Line Blasts, and Jelly Switches.", "AutoAwesome", "4 Boosters"),
        GameFeature("f10", "Beautiful Candy Worlds", "Explore 8 unique candy worlds with custom visual themes and music.", "Public", "8 Worlds"),
        GameFeature("f11", "Offline Play", "Play anytime, anywhere without internet or Wi-Fi required.", "WifiOff", "100% Offline"),
        GameFeature("f12", "Smooth 60 FPS Gameplay", "Ultra fluid candy explosions powered by modern 60 FPS rendering engine.", "Speed", "60 FPS")
    )

    val galleryItems = listOf(
        GalleryItem("g1", "Aurora Valley Entrance", GalleryCategory.SCREENSHOTS, R.drawable.img_world_aurora_1785898394274, "High resolution in-game gameplay view of Aurora Candy Valley."),
        GalleryItem("g2", "Crystal Cavern Crystals", GalleryCategory.SCREENSHOTS, R.drawable.img_world_crystal_1785898410077, "Glowing sugar crystal cavern level map view."),
        GalleryItem("g3", "Neon City Night Skyline", GalleryCategory.SCREENSHOTS, R.drawable.img_world_neon_1785898423809, "Futuristic neon candy city night mode screenshot."),
        GalleryItem("g4", "T.J. Candy Blast Mascot", GalleryCategory.CHARACTERS, R.drawable.img_app_icon_1785898360575, "Official game mascot character and glossy candy emblem art."),
        GalleryItem("g5", "Candy World Banner Art", GalleryCategory.CANDY_ART, R.drawable.img_candy_hero_1785898379829, "Key visual promo art displaying the floating candy islands."),
        GalleryItem("g6", "Color Bomb Special Blast", GalleryCategory.ANIMATIONS, R.drawable.img_world_neon_1785898423809, "Particle explosion sequence when combining rainbow color bombs.")
    )

    val newsItems = listOf(
        NewsItem("n1", "Version 2.4 Update: Neon Dessert Overhaul!", "August 2026", "Update", "New 50 levels added, revamped Neon Dessert City visuals, and new Licorice Blast booster!", "We are thrilled to launch update 2.4 for T.J. Candy Blast! This update brings 50 brand-new levels to Neon Dessert City, optimized 60 FPS candy physics, and enhanced Lucky Spin rewards. Visit https://tjcandyblast.com for patch details.", true),
        NewsItem("n2", "Starlight Sugar Rush Weekend Event", "August 2026", "Event", "Earn 2x Gems and unlimited lives during this weekend's Starlight Sugar Rush!", "Join the global community event from August 7 to August 9. Complete event levels to unlock exclusive avatar frames and 500 bonus gems.", false),
        NewsItem("n3", "Patch Notes v2.3.5 - Balance & Bug Fixes", "July 2026", "Patch Notes", "Improved level 420 difficulty curve, offline sync fix, and faster loading.", "Addressed user reported level progression pacing, optimized memory usage on tablet devices, and enhanced offline saving.", false)
    )

    val faqItems = listOf(
        FaqItem("q1", "Is the game free?", "Yes! T.J. Candy Blast is 100% free to download and play. All levels and worlds can be unlocked through normal gameplay.", "General"),
        FaqItem("q2", "How do I save progress?", "Your progress is automatically saved to local storage via Room database and synced to cloud when connected online.", "Account"),
        FaqItem("q3", "Can I play offline?", "Absolutely! T.J. Candy Blast supports full 100% offline gameplay. You don't need internet or Wi-Fi to play levels.", "Gameplay"),
        FaqItem("q4", "How do boosters work?", "Boosters like the Candy Hammer or Color Bomb can be selected before or during a level to clear obstacles and trigger candy blasts.", "Boosters"),
        FaqItem("q5", "How do I earn rewards?", "You can earn free gems and boosters through Daily Logins, Lucky Spin, Daily Quests, Club Chests, and level stars.", "Rewards")
    )

    val dailyQuests = listOf(
        DailyQuest("dq1", "Match 50 Pink Stars", 50, 32, 50, false),
        DailyQuest("dq2", "Trigger 3 Color Bombs", 3, 2, 75, false),
        DailyQuest("dq3", "Complete 2 Levels", 2, 2, 100, true)
    )

    val leaderboards = listOf(
        LeaderboardUser(1, "SugarKing_99", 98400, "Neon Dessert City"),
        LeaderboardUser(2, "CandyQueen", 89200, "Crystal Sugar Caverns"),
        LeaderboardUser(3, "SweetTooth_Pro", 81500, "Aurora Candy Valley"),
        LeaderboardUser(4, "JellyMaster", 76300, "Starlight Cookie Galaxy"),
        LeaderboardUser(5, "T.J. Gamer (You)", 12500, "Aurora Candy Valley", true)
    )
}
