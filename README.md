# 🎬 Movie App iFarmer

A modern Android movie discovery application built with **Jetpack Compose** and **Clean Architecture** principles. The app fetches movie data from a remote API once and then operates offline, providing a seamless movie browsing experience with wishlist functionality.

## 📱 Features

### Core Features
- ✅ **Splash Screen** - Initial data synchronization with elegant loading animation
- ✅ **Movie List** - Scrollable list of movies with reverse chronological order
- ✅ **Search Movies** - Search by movie title with real-time filtering
- ✅ **Genre Filter** - Filter movies by genre using dropdown selection
- ✅ **Movie Details** - Comprehensive movie information display
- ✅ **Wishlist** - Add/remove movies from personal wishlist
- ✅ **Pagination** - Load 10 movies per page for optimal performance
- ✅ **Offline Mode** - Full functionality after initial data sync
- ✅ **Wishlist Animation** - Smooth animations when adding to wishlist

### Bonus Features
- ✅ **Dark Theme Support** - Automatic light/dark theme switching
- ✅ **Material 3 Design** - Modern UI following Material Design guidelines
- ✅ **Modular Architecture** - Clean separation into app, domain, and data modules
- ✅ **Unit Tests** - Comprehensive testing for business logic
- ✅ **Beautiful Animations** - Compose animations throughout the app
- ✅ **Error Handling** - Graceful error handling with user feedback

## 🏗️ Architecture

The app follows **Clean Architecture** principles with clear separation of concerns:

```
📁 app/                          # Presentation Layer
├── presentation/
│   ├── screen/                  # UI Screens
│   ├── component/               # Reusable UI Components
│   ├── navigation/              # Navigation Logic
│   └── theme/                   # App Theming
│
📁 domain/                       # Business Logic Layer
├── model/                       # Domain Models
├── repository/                  # Repository Interfaces
└── usecase/                     # Business Use Cases
│
📁 data/                         # Data Layer
├── local/                       # Room Database
├── remote/                      # API Integration
├── repository/                  # Repository Implementations
├── mapper/                      # Data Mapping
└── di/                          # Dependency Injection
```

### Architecture Patterns Used
- **MVVM (Model-View-ViewModel)** - For UI state management
- **Repository Pattern** - Single source of truth for data
- **Use Case Pattern** - Encapsulates business logic
- **Dependency Injection** - Using Hilt for clean dependencies

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Design System** | Material 3 |
| **Architecture** | Clean Architecture + MVVM |
| **Database** | Room |
| **Networking** | Retrofit + OkHttp |
| **Serialization** | Kotlinx Serialization |
| **Dependency Injection** | Hilt |
| **State Management** | StateFlow + Compose State |
| **Image Loading** | Coil |
| **Navigation** | Navigation Compose |
| **Testing** | JUnit + Mockito |

## 🚀 Setup Instructions

### Prerequisites
- **Android Studio** Hedgehog | 2023.1.1 or newer
- **JDK 11** or higher
- **Android SDK** with minimum API level 24
- **Git** for version control

### 1. Clone the Repository
```bash
git clone <repository-url>
cd MovieAppIFarmer
```

### 2. Open in Android Studio
1. Open Android Studio
2. Select "Open an existing project"
3. Navigate to the cloned repository folder
4. Wait for Gradle sync to complete

### 3. Build the Project
```bash
./gradlew build
```

### 4. Run the App
1. Connect an Android device or start an emulator
2. Click the "Run" button in Android Studio
3. The app will automatically sync movie data on first launch

### 5. Run Tests
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## 📡 API Integration

The app fetches movie data from:
```
https://raw.githubusercontent.com/erik-sytnyk/movies-list/master/db.json
```

### Data Sync Strategy
- **First Launch**: Downloads and stores all movie data locally
- **Subsequent Launches**: Uses cached data for instant loading
- **No Internet**: Full offline functionality maintained

## 🗄️ Database Schema

### Movies Table
```sql
CREATE TABLE movies (
    id INTEGER PRIMARY KEY,
    title TEXT NOT NULL,
    overview TEXT NOT NULL,
    releaseDate TEXT NOT NULL,
    posterPath TEXT,
    backdropPath TEXT,
    voteAverage REAL NOT NULL,
    voteCount INTEGER NOT NULL,
    genres TEXT NOT NULL,        -- Comma-separated genres
    runtime INTEGER,
    originalLanguage TEXT NOT NULL,
    popularity REAL NOT NULL,
    isWishlisted INTEGER NOT NULL DEFAULT 0
);
```

## 🧪 Testing Strategy

### Unit Tests Coverage
- ✅ **Use Cases** - Business logic validation
- ✅ **Repository** - Data operations testing
- ✅ **ViewModels** - UI state management testing

### Test Examples
```kotlin
// Use Case Testing
@Test
fun `getMovies should return paginated movies`() = runTest {
    // Given
    val expectedMovies = listOf(sampleMovie)
    whenever(repository.getMovies(0, 10, null))
        .thenReturn(flowOf(expectedMovies))
    
    // When & Then
    getMoviesUseCase(0, 10, null).collect { movies ->
        assertEquals(expectedMovies, movies)
    }
}
```

## 🎨 UI/UX Features

### Design Highlights
- **Material 3** design system
- **Adaptive layouts** for different screen sizes
- **Smooth animations** for user interactions
- **Intuitive navigation** between screens
- **Accessibility support** with proper content descriptions

### Screen Breakdown
1. **Splash Screen** - Animated logo with loading states
2. **Movie List** - Grid/list view with search and filter
3. **Movie Details** - Rich movie information with backdrop
4. **Wishlist** - Personal collection management

## 🔧 Configuration

### Build Variants
- **Debug** - Development build with logging
- **Release** - Production build with optimization

### Minimum Requirements
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35

## 📈 Performance Optimizations

- **Lazy Loading** - Efficient pagination implementation
- **Image Caching** - Coil image loading with memory cache
- **Database Indexing** - Optimized queries for search/filter
- **State Management** - Efficient Compose recomposition
- **Memory Management** - Proper lifecycle-aware components

## 🤝 Contributing

### Git Workflow
1. **Feature Branches** - Create feature-specific branches
2. **Commit Messages** - Use conventional commit format
3. **Pull Requests** - Code review before merging
4. **Testing** - Ensure all tests pass before submission

### Code Style
- Follow **Kotlin coding conventions**
- Use **ktlint** for code formatting
- Document public APIs with KDoc
- Write meaningful commit messages

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Author

**iFarmer Development Team**

For questions or support, please contact: [your-email@example.com]

---

### 📱 Screenshots

*Add screenshots of your app here*

---

**Made with ❤️ using Jetpack Compose**
