import PropTypes from 'prop-types';
import Header from './Header';
// import Footer from './Footer';
// import Notification from './Notification';
import styles from './Layout.module.css';

const Layout = ({ children }) => {
  return (
    <div className={styles.layout}>
      <Header />
      
      <main className={styles.main}>
        {children}
      </main>
      
{/*       <Notification /> */}
{/*       <Footer /> */}
    </div>
  );
};

Layout.propTypes = {
  children: PropTypes.node.isRequired,
};

export default Layout;