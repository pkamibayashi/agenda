package agenda;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("##################");
            System.out.println("##### AGENDA #####");
            System.out.println("##################");
            System.out.println(">>>> Contatos <<<<");
            for (Contato contato : agenda.getContatos()) {

                for (Telefone telefone : contato.getTelefones()) {
                    System.out.println(
                            contato.getId() + " | " + contato.getNome() + " | " + "Telefone: " + telefone.getNumero());
                }
            }

            System.out.println(">>>> Menu <<<<");
            System.out.println("1 - Adicionar Contato");
            System.out.println("2 - Remover Contato");
            System.out.println("3 - Editar Contato");
            System.out.println("4 - Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 4) {
                break;
            }

            switch (opcao) {
                case 1:
                    // Adicionar contato
                    System.out.println("Digite o ID do contato:");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Digite o nome do contato:");
                    String nome = scanner.nextLine();
                    Contato novoContato = new Contato();
                    novoContato.setId(id);
                    novoContato.setNome(nome);

                    System.out.println("Digite o DDD do telefone:");
                    String ddd = scanner.nextLine();
                    System.out.println("Digite o número do telefone:");
                    Long numero = scanner.nextLong();
                    scanner.nextLine();

                    Telefone telefone = new Telefone();
                    telefone.setDdd(ddd);
                    telefone.setNumero(numero);

                    if (!agenda.telefonesRegistrados.contains(ddd + numero)) {
                        novoContato.getTelefones().add(telefone);
                        agenda.telefonesRegistrados.add(ddd + numero);
                    } else {
                        System.out.println("Telefone já cadastrado para outro contato!");
                    }

                    agenda.adicionarContato(novoContato);
                    break;
                case 2:
                    // Remover contato
                    System.out.println("Digite o ID do contato a ser removido:");
                    Long idRemover = scanner.nextLong();
                    agenda.removerContato(idRemover);
                    break;
                case 3:
                    // Editar contato
                    System.out.println("Digite o ID do contato a ser editado:");
                    Long idEditar = scanner.nextLong();

                    System.out.println("Digite o novo nome do contato (ou pressione Enter para manter o nome atual):");
                    String novoNome = "";
                    while (novoNome.isEmpty()) {
                        novoNome = scanner.nextLine();
                    }

                    Contato contatoAtualizado = new Contato();
                    contatoAtualizado.setId(idEditar);
                    contatoAtualizado.setNome(novoNome);

                    // ################## TBD ##################
                    // System.out.println(
                    // "Deseja editar o DDD ou o número do telefone? Pressione 'D' para DDD ou 'N'
                    // para Numero:");
                    // String respostaEdicaoTelefone = scanner.nextLine();

                    // if (respostaEdicaoTelefone.equalsIgnoreCase("D")) {
                    // System.out.println("Digite o novo DDD do telefone:");
                    // String novoDdd = scanner.nextLine();

                    // for (Telefone telefoninho : contatoAtualizado.getTelefones()) {
                    // telefoninho.setDdd(novoDdd);
                    // }
                    // } else if (respostaEdicaoTelefone.equalsIgnoreCase("N")) {
                    // System.out.println("Digite o novo número do telefone:");
                    // Long novoNumero = scanner.nextLong();
                    // scanner.nextLine();

                    // for (Telefone telefoninho : contatoAtualizado.getTelefones()) {
                    // telefoninho.setNumero(novoNumero);
                    // }
                    // }

                    agenda.editarContato(idEditar, contatoAtualizado);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

        scanner.close();
    }
}