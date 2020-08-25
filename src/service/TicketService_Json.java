package service;

import model.dao.TicketDao;
import model.entity.Ticket;
import util.DateUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/api/tickets/json")
public class TicketService_Json {
    TicketDao ticketDao = new TicketDao();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ticket getTicket(@PathParam("id") int id) {

        Ticket ticket = ticketDao.findById(id);
        ticket.setDate(DateUtil.formatDate(ticket.getDate(), -6));
        return ticket;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ticket> getAllTickets() {

        List<Ticket> allTickets = ticketDao.findAll();
        for (Ticket ticket : allTickets)
            ticket.setDate(DateUtil.formatDate(ticket.getDate(), -6));
        return allTickets;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTicket(@PathParam("id") int id, Ticket ticket) throws CloneNotSupportedException {

        if (ticketDao.findById(id) != null) {
            ticket.setId(id);
            ticket.setDate(DateUtil.formatDate(ticket.getDate(), 8));
            ticketDao.update(ticket);
            return Response.status(200).entity("{\"response\" : \"Ticket with id " + ticket.getId() + " successfully updated.\"}").build();
        } else
            return Response.status(404).entity("{\"response\" : \"There is no such ticket ID.\"}").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewTicket(Ticket ticket) {

        ticket.setDate(DateUtil.formatDate(ticket.getDate(), 8));
        int id = ticketDao.insert(ticket);
        return Response.status(200).entity("{\"response\" : \"Ticket successfully created.(TICKET ID: " + id + ")\"}").build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTicket(@PathParam("id") int id) {

        boolean isSuccessFull = ticketDao.delete(id);
        if (isSuccessFull)
            return Response.status(200).entity("{\"response\" : \"Ticket with id " + id + " successfully deleted.\"}").build();
        else
            return Response.status(404).entity("{\"response\" : \"There is no such ticket ID.\"}").build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAllTickets() {

        ticketDao.deleteAll();
        return Response.status(200).entity("{\"response\" : \"All tickets successfully deleted.\"}").build();
    }
}