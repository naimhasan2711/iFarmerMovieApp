# 🎬 iFarmerMovieApp

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
- ✅ **Beautiful Animations** - Compose animations throughout the app
- ✅ **Error Handling** - Graceful error handling with user feedback

## 🏗️ Architecture

The app follows **Clean Architecture** principles with clear separation of concerns:

```
📁 app/                          
├── presentation/
│   ├── ui/                      # UI Screens
│   │   ├── details/             # Reusable UI Components
│   │   ├── home/                # Reusable UI Components
│   │   ├── splash/              # Reusable UI Components
│   │   ├── wishlist/            # Reusable UI Components
│   ├── viewmodel/               # viewmodels
├── domain/                      # Business Logic Layer
│   ├── models/                  # Domain Models
│   │   ├── local/               # Domain Models
│   │   └── remote/              # Domain Models
│   ├── repository/              # Repository Interfaces
│   ├── usecase/                 # Business Use Cases
│   └── repositoryimpl/          # Repository Implementations
├── data/                        # Data Layer
│   ├── datasource/              # Room Database
│   │   ├── datasourceimpl/      # Room Database
│   │   ├── local/               # Room Database
│   │   └── remote/              # Room Database
│   └── mapper/                  # Data Mapping
├── navigation/                  # Navigation Logic
│   ├── NavGraph.kt              # Navigation Logic
│   └── Screens.kt               # Navigation Logic
├── di/                          # Dependency Injection
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
| **Dependency Injection** | Hilt |
| **State Management** | StateFlow + Compose State |
| **Image Loading** | Coil |
| **Navigation** | Navigation Compose |

## 🚀 Setup Instructions

### Prerequisites
- **Android Studio** Hedgehog | 2023.1.1 or newer
- **JDK 11** or higher
- **Android SDK** with minimum API level 24
- **Git** for version control

### 1. Clone the Repository
```bash
git clone https://github.com/naimhasan2711/iFarmerMovieApp.git
cd iFarmerMovieApp
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
MovieEntity(
    val id: Int,
    val title: String,
    val plot: String,
    val posterUrl: String,
    val runtime: String,
    val year: String,
    val director: String,
    val actors: String,
    val genres: List<String>,
    val isFavorite: Boolean 
)
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
2. **Movie List** - list view with search and filter
3. **Movie Details** - Rich movie information with backdrop
4. **Wishlist** - Personal collection management

## 🔧 Configuration


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

### Code Style
- Follow **Kotlin coding conventions**
- Write meaningful commit messages


## 👥 Author

**Md Nakibul Hassan**

For questions or support, please contact: [nakibhasan2711@gmail.com]

---

### 📱 Screenshots

*Add screenshots of your app here*

---

**Made with ❤️ using Kotlin & Jetpack Compose**
