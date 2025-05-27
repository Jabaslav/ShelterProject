import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import { NotificationProvider } from './context/NotificationContext'
import { AppProvider } from './context/AppContext'
import App from './App'
import './styles/global.css'

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <NotificationProvider>
       <AppProvider>
        <AuthProvider>
            <App/>
        </AuthProvider>
       </AppProvider>
      </NotificationProvider>
    </BrowserRouter>
  </React.StrictMode>
)