package model.dao;

import model.entity.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TicketDao {
    private SessionFactory sessionFactory = SessionFactoryConfig.getSessionFactory();

    public int insert(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(ticket);
        transaction.commit();
        session.close();
        return id;
    }

    public void update(Ticket ticket) throws CloneNotSupportedException {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Ticket loadedTicket = session.load(Ticket.class, ticket.getId());
        loadedTicket = (Ticket) ticket.clone();
        session.update(loadedTicket);
        transaction.commit();
        session.close();
    }

    public boolean delete(int ticketId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Ticket loadedTicket = session.get(Ticket.class, ticketId);
        if (loadedTicket == null) return false;
        session.delete(loadedTicket);
        transaction.commit();
        session.close();
        return true;
    }

    public Ticket findById(int ticketId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Ticket loadedTicket = session.get(Ticket.class, ticketId);
        transaction.commit();
        session.close();
        return loadedTicket;
    }

    public void deleteAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from model.Ticket").executeUpdate();
        transaction.commit();
        session.close();
    }

    public List<Ticket> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Ticket> allTickets = session.createQuery("from model.Ticket").getResultList();
        transaction.commit();
        session.close();
        return allTickets;
    }
}
