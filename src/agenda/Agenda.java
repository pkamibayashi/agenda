package agenda;

import java.util.*;
import java.io.*;

public class Agenda {

    public final Map<Long, Contato> contatos = new HashMap<>();
    public final Set<String> telefonesComDdd = new HashSet<>();
    public final Set<String> telefonesRegistrados = new HashSet<>();

    public void adicionarContato(Contato contato) {
        if (!contatos.containsKey(contato.getId())) {
            for (Telefone telefone : contato.getTelefones()) {
                if (!adicionarTelefone(telefone)) {
                    System.out.println("Telefone já cadastrado");
                    return;
                }
            }

            contatos.put(contato.getId(), contato);
            salvarContatos();
        } else {
            System.out.println("Não é permitido armazenar contatos com o mesmo id");
        }

    }

    public boolean adicionarTelefone(Telefone telefone) {
        Set<String> telefonesTemp = new HashSet<>();
        String chaveTelefone = telefone.getDdd() + telefone.getNumero();
        telefonesTemp.add(chaveTelefone);
        telefonesComDdd.addAll(telefonesTemp);
        return true;
    }

    public void removerContato(Long id) {
        Contato contato = contatos.remove(id);
        if (contato != null) {
            for (Telefone telefone : contato.getTelefones()) {
                telefonesRegistrados.remove(telefone.getDdd() + telefone.getNumero());
            }

            salvarContatos();
        }
    }

    public void editarContato(Long id, Contato contatoAtualizado) {
        if (contatos.containsKey(id)) {
            Contato contatoOriginal = contatos.get(id);

            if (!contatoAtualizado.getNome().isEmpty()) {
                contatoOriginal.setNome(contatoAtualizado.getNome());
            }

            List<Telefone> telefonesAtualizados = new ArrayList<>(contatoAtualizado.getTelefones());
            for (Telefone telefoneAtualizado : telefonesAtualizados) {
                for (Telefone telefoneOriginal : contatoOriginal.getTelefones()) {
                    if (telefoneAtualizado.equals(telefoneOriginal)) {
                        telefoneOriginal.setDdd(telefoneAtualizado.getDdd());
                        telefoneOriginal.setNumero(telefoneAtualizado.getNumero());
                        break;
                    }
                }
            }

            salvarContatos();
        }
    }

    public Collection<Contato> getContatos() {
        return contatos.values();
    }

    public void salvarContatos() {
        System.out.println("Salvando contatos...");
        File arquivo = new File("contatos.txt");
        try (PrintWriter out = new PrintWriter(new FileWriter(arquivo))) {

            for (Contato contato : contatos.values()) {
                out.println(contato.getId() + " | " + contato.getNomeCompleto() + " | " + contato.getTelefones());
                for (Telefone telefone : contato.getTelefones()) {
                    out.println(telefone.getDdd() + " | " + telefone.getNumero());
                }
                out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}