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
                    return; // Telefone já cadastrado
                }
            }

            contatos.put(contato.getId(), contato);
            salvarContatos();
        } else {
            System.out.println("Não é permitido armazenar contatos com o mesmo id");
        }

    }

    public boolean adicionarTelefone(Telefone telefone) {
        Set<String> telefonesTemp = new HashSet<>(); // Cria um novo conjunto para armazenar os telefones adicionados
        String chaveTelefone = telefone.getDdd() + telefone.getNumero();
        telefonesTemp.add(chaveTelefone);
        telefonesComDdd.addAll(telefonesTemp); // Adiciona os novos telefones ao conjunto principal
        return true;
    }

    public void removerContato(Long id) {
        Contato contato = contatos.remove(id);
        if (contato != null) {
            for (Telefone telefone : contato.getTelefones()) {
                telefonesRegistrados.remove(telefone.getDdd() + telefone.getNumero());
            }
        }
    }

    public void editarContato(Long id, Contato contatoAtualizado) {
        if (contatos.containsKey(id)) {
            Contato contatoOriginal = contatos.get(id);

            // Editar nome
            if (!contatoAtualizado.getNome().isEmpty()) {
                contatoOriginal.setNome(contatoAtualizado.getNome());
            }

            // Editar telefones
            for (Telefone telefoneAtualizado : contatoAtualizado.getTelefones()) {
                String chaveTelefone = telefoneAtualizado.getDdd() + telefoneAtualizado.getNumero();

                if (contatoOriginal.getTelefones().contains(telefoneAtualizado)) {
                    // Editar DDD e número existentes
                    Telefone telefoneOriginal = contatoOriginal.getTelefones().get(
                            contatoOriginal.getTelefones().indexOf(telefoneAtualizado));
                    telefoneOriginal.setDdd(telefoneAtualizado.getDdd());
                    telefoneOriginal.setNumero(telefoneAtualizado.getNumero());
                } else if (!telefonesRegistrados.contains(chaveTelefone)) {
                    // Adicionar novo telefone
                    contatoOriginal.getTelefones().add(telefoneAtualizado);
                    telefonesRegistrados.add(chaveTelefone);
                }
            }

            // Remover telefones não presentes no contato atualizado
            for (Telefone telefoneOriginal : contatoOriginal.getTelefones()) {
                if (!contatoAtualizado.getTelefones().contains(telefoneOriginal)) {
                    contatoOriginal.getTelefones().remove(telefoneOriginal);
                    telefonesRegistrados.remove(telefoneOriginal.getDdd() + telefoneOriginal.getNumero());
                }
            }

            // Atualizar contato
            contatos.put(id, contatoOriginal);
        }
    }

    public Collection<Contato> getContatos() {
        return contatos.values();
    }

    public void salvarContatos() {
        File arquivo = new File("contatos.txt");
        try (PrintWriter out = new PrintWriter(new FileWriter(arquivo))) {

            // Escreve os dados dos contatos no arquivo
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