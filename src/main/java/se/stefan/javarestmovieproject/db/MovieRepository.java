package se.stefan.javarestmovieproject.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import se.stefan.javarestmovieproject.model.Movie;

import java.util.List;

/**
 * MovieRepository-klassen kommer att hantera all interaktion som görs med databasen
 * CDI-annoteringen
 * @ApplicationScoped definierar en bean(klass) som lever under hela applikationens livslängd.
 * Den gör klassen till en så kallad Singleton, att endast en instans skapas.
 * Dessutom gör den att vi kan inject MovieRepository i andra klasser.
 * CDI-annoteringen
 * @Transactional gör att varje metod i denna klass körs i en transaktion som applikationsservern startar upp.
 * Med andra ord så hanteras transaktioner automatiskt.
 * Varje metod i klassen kommer skapa upp en transaktion och avsluta en transaktion.
 */
@ApplicationScoped
@Transactional
public class MovieRepository {

    /*
     * Annotationen @PersistenceContext injicerar EntityManager
     * Det gör att man inte behöver skriva = new EntityManager()
     * @PersistenceContext associerar också vår EntityManager till våra properties i persistence.xml och vår databas
     */
    @PersistenceContext
    /*
     * Interagerar med persistence context
     * EntityManager objektet tillåter oss att hantera entiteter och deras livscykel,
     * såsom att spara och hämta data i vår databas
     * Nedan deklareras vår EntityManager
     */
    private EntityManager entityManager;

    /**
     * Metoden hämtar en lista med Movies från databasen
     * @return en lista med Movies
     * */
    public List<Movie> findMovies() {
        // Med sql kan vi hämta våra kolumner från tabellen.
        // SELECT * FROM appmovies, innebär att vi hämtar query för alla kolumner i tabellen appmovies.
        String sql = "SELECT * FROM appmovies";
        //Vi använder metoden createNativeQuery i EntityManager klassen
        //Detta betyder att en instans av Query skapas för att köra en SQL sats
        //Query är en förfrågan om information från en databas
        Query query = entityManager.createNativeQuery(sql);
        //Genom att anropa getResultList får vi tillbaka en lista med Movies
        List<Movie> movies = query.getResultList();
        //metoden returnerar listan movies
        return movies;
    }

    /**
     * Lägger till en ny Movie entitet i databasen
     * @param movie, Movie entiteten som läggs till
     */
    public void addNewMovie(Movie movie) {
        //Vi anropar metoden persist(), som finns i EntityManager.
        //Metoden kommer skapar en ny Movie, lägger till en till ny entitet i databasen
        entityManager.persist(movie);
    }

    /**
     * Hittar en Movie från databasen baserat på dess ID
     * @param id är filmens id som vi hämtar
     * @return movie med det specifika id:et
     */
    public Movie findMovieById(Long id) {
        //Vi anropar metoden find i EntityManager som hittar en entitet baserat på Id
        //Som parametrar i .find() måste vi säga vilken klass som ska användas och filmens id
        return entityManager.find(Movie.class, id);
    }

    /**
     * Uppdaterar en Movie entitet i databasen
     * @param movie som entiteten ska uppdateras/merge med
     */
    public void updateMovie(Movie movie) {
        //Vi anropar metoden i EntityManager som heter merge
        //som är till för att hantera förändringar på ett objekt
        //med hjälp av denna metod kan vi uppdatera vår movie som används i parametern
        entityManager.merge(movie);
    }

    /**
     * Tar bort en Movie från databasen
     * @param id är movie id som hämtas
    */
    public void deleteMovie(Long id) {
        //Vi skapar en ny instans av klassen movie och använder findMovieById metoden
        // och sen ger den det värde som filmen med det specifika id:et har
        Movie movieToDelete = findMovieById(id);

        //Om entiteten finns i databasen, kan metoden remove köras
        if(movieToDelete != null) {
            //Metoden remove i EntityManager kommer ta bort entiteten vi hittade med
            //vårt Id
            //remove kommer att ta bort en entitet från vår databas
            entityManager.remove(movieToDelete);
        }
    }


}
