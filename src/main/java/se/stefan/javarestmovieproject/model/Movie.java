package se.stefan.javarestmovieproject.model;
import jakarta.persistence.*;

/**
 * Movie-klassen syfte är att vara en JPA-entitet som i detta fall kommer representera en Movie i systemet
 * Annoteringen @Entity gör att klassen representerar en tabell i databasen.
 * Och med @Table skriver vi tabellens namn.
*/
@Entity
@Table(name = "appmovies")
public class Movie {

    /**
     * En tom constructor utan argument för att skapa och initiera objekt
     * Då Entity behöver det
     */
    public Movie() {
    }

    //Vi genererar en Id som kommer att användas som vår primary key i tabellen.
    //Annoteringen GeneratedValue gör att databasen själv skapar värdet för Id
    //Där kolumnens namn är id och den får inte var tom
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    //Vi använder datatypen Long då den kan hantera större tal, i jämförelse med int
    private Long id;

    //Här deklarer vi våra private attribut
    private String title;
    private String genre;
    private int productionYear;
    private String plot;
    private String director;

    /**
     * Get-metod för attributen id, hämtar värdet på attributen
     * @return id värdet för attributen
     */
    public Long getId() {
        return id;
    }
    /**
     * Set-metod för attributen id, sätter ny värde på attributen
     * @param id det nya värdet som ska sättas
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * En constructor med argument för att kunna skapa och initiera vårt objekt
     */
    public Movie(String title, String genre, int productionYear, String plot, String director) {
        this.title = title;
        this.genre = genre;
        this.productionYear = productionYear;
        this.plot = plot;
        this.director = director;
    }

    /**
     * Get-metod för attributen title, hämtar värdet på attributen
     * @return title värdet för attributen
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set-metod för attributen title, sätter ny värde på attributen
     * @param title det nya värdet som ska sättas
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Get-metod för attributen genre, hämtar värdet på attributen
     * @return genre värdet för attributen
     */
    public String getGenre() {
        return genre;
    }
    /**
     * Set-metod för attributen genre, sätter ny värde på attributen
     * @param genre det nya värdet som ska sättas
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    /**
     * Get-metod för attributen productionYear, hämtar värdet på attributen
     * @return productionYear värdet för attributen
     */
    public int getProductionYear() {
        return productionYear;
    }
    /**
     * Set-metod för attributen productionYear, sätter ny värde på attributen
     * @param productionYear det nya värdet som ska sättas
     */
    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }
    /**
     * Get-metod för attributen plot, hämtar värdet på attributen
     * @return plot värdet för attributen
     */
    public String getPlot() {
        return plot;
    }
    /**
     * Set-metod för attributen plot, sätter ny värde på attributen
     * @param plot det nya värdet som ska sättas
     */
    public void setPlot(String plot) {
        this.plot = plot;
    }
    /**
     * Get-metod för attributen director, hämtar värdet på attributen
     * @return director värdet för attributen
     */
    public String getDirector() {
        return director;
    }
    /**
     * Set-metod för attributen director, sätter ny värde på attributen
     * @param director det nya värdet som ska sättas
     */
    public void setDirector(String director) {
        this.director = director;
    }
}
