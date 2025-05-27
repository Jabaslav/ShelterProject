import { Suspense, lazy } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { useAuth } from './context/AuthContext'
import { useNotification } from './context/NotificationContext'
import Layout from './components/shared/Layout'
import Loader from './components/ui/Loader'
import ProtectedRoute from './components/auth/ProtectedRoute'
import styles from './App.module.css'

// Ленивая загрузка страниц
const Home = lazy(() => import('./pages/Home'))
const Login = lazy(() => import('./pages/Auth/Login'))
const Register = lazy(() => import('./pages/Auth/Register'))
const Profile = lazy(() => import('./pages/Profile/Profile'))
const EditProfile = lazy(() => import('./pages/Profile/EditProfile'))
// const Pets = lazy(() => import('./pages/Pets/PetDetails'))
const PetDetails = lazy(() => import('./pages/Pets/PetDetails'))
// const Shelters = lazy(() => import('./pages/Shelters/Shelters'))
// const ShelterProfile = lazy(() => import('./pages/Shelters/ShelterProfile'))
// const Posts = lazy(() => import('./pages/Posts/Posts'))
const PostDetails = lazy(() => import('./pages/Posts/PostDetails'))
const PostCreate = lazy(() => import('./pages/Posts/PostCreate'))
const Favorites = lazy(() => import('./pages/Favorites'))
// const NotFound = lazy(() => import('./pages/NotFound'))



const App = () => {
//   const { isAuthenticated } = useAuth()
    const { showNotification } = useNotification();

  return (
    <Layout className={styles.Layout}>
      <Suspense  fallback={<Loader fullPage />}>
        <Routes>
          {/* Публичные маршруты */}
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
{/*           <Route path="/pets" element={<Pets />} /> */}
          <Route path="/pets/:id" element={<PetDetails />} />
{/*           <Route path="/shelters" element={<Shelters />} /> */}
{/*           <Route path="/shelters/:id" element={<ShelterProfile />} /> */}
{/*           <Route path="/posts" element={<Posts />} /> */}
          <Route path="/posts/:id" element={<PostDetails />} />

          {/* Защищенные маршруты */}
          <Route element={<ProtectedRoute />}>
            <Route path="/profile" element={<Profile />} />
            <Route path="/profile/edit" element={<EditProfile />} />
            <Route path="/posts/create" element={<PostCreate />} />
            <Route path="/favorites" element={<Favorites />} />
            
{/*              */}{/* Маршруты для администраторов приютов */}
{/*             {isAuthenticated?.user?.role === 'SHELTER_ADMIN' && ( */}
{/*               <Route path="/pets/new" element={<PetCreate />} /> */}
{/*             )} */}
          </Route>

          {/* Не найден */}
{/*            <Route path="*" element={<NotFound />} /> */}
        </Routes>
      </Suspense>
    </Layout>
  )
}

export default App