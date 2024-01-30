package agenda;

import java.util.List;
import java.util.ArrayList;

public class Contato {

    public Long id;
    public String nome;
    public String sobreNome;
    public List<Telefone> telefones;

    public Contato() {
        telefones = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeCompleto() {
        if (sobreNome != null && !sobreNome.isEmpty()) {
            return nome + " " + sobreNome;
        }
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }
}