# Flight Booking System - Frontend

## ✈️ Overview
This is a Vue.js-based frontend application for a flight booking system. It allows users to view available flights, select seats, and complete bookings with an intuitive UI.

## 📦 Installation & Setup
### 1️⃣ Clone the Repository
```sh
git clone https://github.com/your-repo/flight-booking-frontend.git
cd flight-booking-frontend
```

### 2️⃣ Install Dependencies
```sh
npm install
```

### 3️⃣ Run the Development Server
```sh
npm run dev
```
This will start the frontend application at `http://localhost:5173/` (default Vite port).

### 4️⃣ Build for Production
```sh
npm run build
```
This will generate an optimized build in the `dist/` directory.

---

## 🛠 Dependencies
The project uses the following dependencies:

### 📌 **Production Dependencies**
These are required for the application to function:
```json
"dependencies": {
  "axios": "^1.8.4",                    // HTTP client for API calls
  "vue": "^3.5.13",                      // Vue.js 3 framework
  "vue-router": "^4.5.0",                // Vue Router for page navigation
  "vue-slider-component": "^3.2.24"      // Custom slider component
}
```

### 🛠 **Development Dependencies**
These are useful for development but not required in production:
```json
"devDependencies": {
  "@vitejs/plugin-vue": "^5.2.1",        // Vite plugin for Vue SFCs
  "vite": "^6.2.0",                      // Modern development & build tool
  "vite-plugin-vue-devtools": "^7.7.2"   // Vue DevTools plugin for debugging
}
```

### ✅ **How to run on localhost**

- Clone the project to your IDE. Find application.properties file and configure database requirements. Next open the Terminal and run ./gradlew build. Now you can run the LennuVeebirakendus file.
- As a first step make sure to create atleast 3 different SeatClasses (necessary step)
---

## 📜 Features
- **Flight Details View**: View information about flights, including price and schedule.
- **Seat Selection**: Choose available seats dynamically.
- **Booking System**: Enter passenger details and confirm seat selection.
- **Real-Time Updates**: Fetch and display seat availability dynamically.
- **Sorting & Filtering**: Sort seats and filter by criteria like price.

---

## 🚀 Future Improvements
- Add **user authentication** for a personalized experience.
- Improve **seat selection UI** for better visualization.
- Implement **payment gateway integration**.

---

## 🤝 Contributing
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature-branch`).
3. Commit changes (`git commit -m 'Add new feature'`).
4. Push to branch (`git push origin feature-branch`).
5. Open a **Pull Request**.

---

## 📄 License
This project is licensed under the **MIT License**.

---

## 💬 Need Help?
For issues, please open an issue on GitHub.
