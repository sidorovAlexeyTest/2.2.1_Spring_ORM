package hiber.dao;

import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String model, int series) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            Query query = session.createQuery("FROM User WHERE car.model = :model AND car.series = :series");
            query.setParameter("model", model)
                    .setParameter("series", series);
            List list = query.getResultList();
            transaction.commit();
            return !list.isEmpty() ? (User) list.get(0) : new User("-1", "-1", "-1");
        } catch (HibernateException e) {
            e.printStackTrace();
            try {
                if (transaction != null) transaction.rollback();
            } catch (HibernateException he) {
                e.printStackTrace();
            }
        } finally {
            try {
                if(session != null) session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return new User("-1", "-1", "-1");
    }

}
