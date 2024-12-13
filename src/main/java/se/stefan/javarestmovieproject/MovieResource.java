package se.stefan.javarestmovieproject;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.stefan.javarestmovieproject.db.MovieRepository;
import se.stefan.javarestmovieproject.model.Movie;

import java.util.List;

/**
 * MovieResource-klassen kommer vara en REST:ful resurs som gör att den kan hantera HTTP-förfrågningar
 * I detta fall använder vi metoder i MovieRepository för att hantera HTTP-förfrågningar
 * Annoteringen @Path definierar vilken URL som ska mappas till klassen
 */
@Path("/movies")
public class MovieResource {

    /*
     * Med @Inject kan vi injicera klassen MovieRepository, jakarta hjälper oss då att skapa en instans av
     * klassen, så = new movieRepository();
     */
    @Inject
    private MovieRepository movieRepository;

    /**
     * Hämtar en lista på alla movies i tabellen från vår databas
     * Annoteringen @GET anger att metoden hanterar GET-förfrågningar
     * Annoteringen @Produces anger att svaret ges i JSON-format
     * @return en lista med movies från systemet
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movie> getMovies() {
        //Här anropar vi och returnerar en lista med alla Movie:s i tabellen
        //Med hjälp av metoden findMovies i klassen MovieRepository kan vi hämta listan med vår data
        return movieRepository.findMovies();
    }

    /**
     * Hämtar en specifik movie baserat på dess ID
     * Annoteringen @GET anger att metoden hanterar GET-förfrågningar
     * Annoteringen @Path definierar att metoden mappas till URL-sökvägen /movies/id
     * Annoteringen @Produces anger att svaret ges i JSON-format
     * Med @PathParam går det att läsa id från URL:en
     * @param id, movie id som ska hämtas
     * @return en Response statuskod 404 NOT_FOUND eller 200 OK och movie data
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("id") Long id) {
        //Vi anropar findMovieById metoden som finns i MovieRepository, skickar in id som parameter
        //och då får vi tillbaka en Movie
        Movie movie = movieRepository.findMovieById(id);
        //Om movie är lika med null så returnerar vi Response-typ 404 NOT FOUND
        // och ett meddelande "Movie not found"
        if (movie == null) {
            //Vi använder Response-typen 404 NOT FOUND (Ej hittad) och ett meddelande till användaren
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found").build();
        }
        //Om Movie vi söker efter finns returnerar vi en Response-typ, 200 OK (hittad)
        return Response.ok(movie).build();
    }

    /**
     * Lägger till en ny movie i databasen
     * Annoteringen @POST anger att metoden hanterar POST-förfrågningar
     * Annoteringen @Consumes vilket mediaformat den ska få in i förfrågan, vilket är JSON-format
     * @param movie som ska läggas till i systemet
     * @return en Response statuskod 201 CREATED
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMovie(Movie movie) {
        //Vi anropar addNewMovie metoden från MovieRepository och skickar in movie
        //Därmed läggs en ny Movie in i tabellen
        movieRepository.addNewMovie(movie);
        //Sedan returnerar vi en Response-typen 201 CREATED, och ett meddelande "Movie added"
        return Response.status(Response.Status.CREATED).entity("Movie added").build();
    }

    /**
     * Hittar en specifik movie baserat på dess ID och uppdaterar sedan entiteten med ny data
     * Annoteringen @PUT anger att metoden hanterar PUT-förfrågningar
     * Annoteringen @Path definierar att metoden mappas till URL-sökvägen movies/id
     * Annoteringen @Consumes vilket mediaformat den ska få in i förfrågan, vilket är JSON-format
     * Med @PathParam går det att läsa id från URL:en
     * @param id för movie som hämtas och updatedMovie en Movie med ny data som ska uppdatera entiteten som hämtas
     * @return Response statuskod 404 NOT_FOUND eller 200 OK
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateThisMovie(@PathParam("id") Long id, Movie updatedMovie) {
        //Vi anropar metoden findMovieById och hämtar ut en Movie baserat på dess ID
        Movie currentMovie = movieRepository.findMovieById(id);
        //Om currentMovie är lika med null så skickar vi tillbaka en 404
        if (currentMovie == null) {
            //Vi använder Response-typen 404 NOT FOUND (Ej hittad) och ett meddelande till användaren,
            // "Movie not found" Och därmed avslutas metoden
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found").build();
        }

        //Här kan vi uppdatera våra attribut, vi sätter nya värden på vår Movie som hämtas från databasen,
        //i detta fall currentMovie, vi sätter nya värdet genom vår parameter updatedMovie
        //Här uppdaterar vi title
        currentMovie.setTitle(updatedMovie.getTitle());
        //uppdaterar genre
        currentMovie.setGenre(updatedMovie.getGenre());
        //uppdaterar productionYear
        currentMovie.setProductionYear(updatedMovie.getProductionYear());
        //uppdaterar plot
        currentMovie.setPlot(updatedMovie.getPlot());
        //uppdaterar director
        currentMovie.setDirector(updatedMovie.getDirector());

        //Sen anropar vi på metoden updateMovie som finns i MovieRepository,
        // och då uppdateras vi Movie-objektet med ny data
        movieRepository.updateMovie(currentMovie);
        //Till slut returnerar vi en Response-typ, 200 OK och ett meddelande "Movie updated successfully"
        return Response.ok("Movie updated successfully").build();
    }

    /**
     * Tar bort en movie entitet från databasen
     * Annoteringen @DELETE anger att metoden hanterar DELETE-förfrågningar
     * Annoteringen @Path definierar att metoden mappas till URL-sökvägen /movies/id
     * Med @PathParam läser man id från URL:en
     * @param id från movie som ska hämtas
     * @return en Response 404 NOT_FOUND eller 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteThisMovie(@PathParam("id") Long id) {
        //Vi anropar metoden findMovieById och hämtar ut en Movie baserat på dess ID
        Movie currentMovie = movieRepository.findMovieById(id);
        //Om currentMovie är lika med null så skickar vi tillbaka en 404
        if (currentMovie == null) {
            //Vi använder Response-typen 404 NOT FOUND (Ej hittad) och ett meddelande till användaren,
            // "Movie not found", därmed avslutas metoden
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found").build();

        }
        //Vi anropar deleteMovie metoden från MovieRepository, och metoden tar bort den hittade Movie entiteten
        //baserat på id parametern
        movieRepository.deleteMovie(id);

        //returnerar en Response-typ, 200 OK och ett meddelande "Movie deleted successfully"
        return Response.ok("Movie deleted successfully").build();

    }



}
