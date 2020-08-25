package service;

import model.dao.TicketDao;
import model.entity.Ticket;
import util.DateUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Path("/api/tickets")
public class TicketService_PlainText {
    TicketDao ticketDao = new TicketDao();

    @GET
    @Path("/{id}")
    @Produces("text/plain")
    public Response getTicket(@PathParam("id") int id) {

        Ticket ticket = ticketDao.findById(id);
        if (ticket != null) {
            return Response.status(200).entity(ticket.toString()).build();
        } else
            return Response.status(404).entity("There is no such ticket ID.").build();
    }

    @GET
    @Produces("text/plain")
    public Response getAllTickets() {

        List<Ticket> allTickets = ticketDao.findAll();
        if (allTickets.size() != 0) {
            String responseText = "";
            for (Ticket ticket : allTickets) {
                responseText += ticket.toString() + " ,";
            }
            responseText = responseText.substring(0, responseText.length() - 1);
            return Response.status(200).entity("Tickets are " + responseText).build();
        } else
            return Response.status(404).entity("There are no tickets.").build();
    }

    @PUT
    @Path("/{id}")
    @Produces("text/plain")
    public Response updateTicket(@PathParam("id") int id,
                                 @QueryParam("owner") String owner,
                                 @QueryParam("origin") String origin,
                                 @QueryParam("destination") String destination,
                                 @QueryParam("date") String dateStr,
                                 @QueryParam("flight_number") int flight_number)
            throws ParseException, CloneNotSupportedException {

        if (ticketDao.findById(id) != null) {
            Date date = DateUtil.convertStrToDate(dateStr);
            Ticket ticket = new Ticket(id, owner, origin, destination, date, flight_number);

            ticketDao.update(ticket);
            return Response.status(200).entity("Ticket with id " + id + " successfully updated.").build();

        } else
            return Response.status(404).entity("There is no such ticket ID.").build();
    }

    @POST
    @Produces("text/plain")
    public Response addNewTicket(@QueryParam("owner") String owner,
                                 @QueryParam("origin") String origin,
                                 @QueryParam("destination") String destination,
                                 @QueryParam("date") String dateStr,
                                 @QueryParam("flight_number") int flight_number) throws ParseException {

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        Ticket ticket = new Ticket(owner, origin, destination, date, flight_number);
        int id = ticketDao.insert(ticket);
        return Response.status(200).entity("Ticket successfully created.\nTICKET ID: " + id).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("text/plain")
    public Response deleteTicket(@PathParam("id") int id) {

        boolean isSuccessFull = ticketDao.delete(id);
        if (isSuccessFull)
            return Response.status(200).entity("Ticket with id " + id + " successfully deleted.").build();
        else
            return Response.status(404).entity("There is no such ticket ID.").build();
    }

    @DELETE
    @Produces("text/plain")
    public Response deleteAllTickets() {

        ticketDao.deleteAll();
        return Response.status(200).entity("All tickets successfully deleted.").build();
    }
}