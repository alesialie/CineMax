package cinemax;

import java.util.ArrayList;
import java.util.List;

/** gestione dati fatta in un altro file**/

public class GestoreProiezioni {
    private List<Proiezione> proiezioni;

    public GestoreProiezioni(){
        this.proiezioni = new ArrayList<>();
    }

    /** ricerca delle proiezioni tramite titolo**/
    public List<Proiezione> cercaTitolo(String Titolo) {
        List<Proiezione> risultato = new ArrayList<>();

        if (Titolo == null || Titolo.isEmpty()) {
            return proiezioni;
        }

        for(Proiezione p : proiezioni){
            String TitoloFilm = p.getFilm().getTitolo().toLowerCase();
            if(TitoloFilm.contains(Titolo.toLowerCase())){
                risultato.add(p);
            }
        }
        return risultato;
    }

}