package atm_sotelli;

// IMPORTACAO DE BIBLIOTECAS
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ATM_Sotelli {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); // INICIALIZACAO DO SCANNER

        // DECLARACOES 
        int operacao,
                i = 0,
                valorInval = 0,
                saldo = 0,
                valorSaque = 0,
                totalSaque = 0,
                restoSaque = 0,
                aux = 0,
                contaExtrato = 0,
                valorextrato = 0;
        String texto1;
        int[] valorNotas = {5, 10, 20, 50, 100}; // ARRAY COM O VALOR DE CADA NOTA
        int[] quantidadeNotas = {10, 10, 10, 10, 10}; // ARRAY COM A QUANTIDADE DE CADA NOTA
        int[] notasSaque = {0, 0, 0, 0, 0}; // ARRAY COM AS NOTAS SACADAS
        Extrato[] extratos = new Extrato[20]; // ARRAY DE EXTRATOS PERMITE 20 EXTRATOS
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"); // dtf indica o formato da data a ser exibida
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        // DECLARACOES
        for (i = 4; i > -1; i--) {
            saldo = saldo + quantidadeNotas[i] * valorNotas[i];
        }
        System.out.println("Este caixa tem: " + saldo + ",00 R$\n");
        for (i = 0; i < 5; i++) {
            texto1 = (quantidadeNotas[i] > 1 || quantidadeNotas[i] == 0) ? " notas" : " nota";
            System.out.println(+quantidadeNotas[i] + texto1 + " de " + valorNotas[i] + " R$");
        }
        do {
            System.out.println("\nQual operacao deseja executar?\n(0) Deposito \n(1) Saque \n(2) Extrato\n(3) Saldo\n(4) Zerar Caixa\n(5) Sair");
            operacao = scan.nextInt();
            switch (operacao) {
                case 0:
                    System.out.println("Carregar Caixa\n\nDigite a quantidade depositar");
                    valorextrato = 0;
                    for (i = 0; i < 5; i++) { // SABENDO QUE SAO 5 TIPOS DE NOTA EVITA-SE USAR array.length 
                        System.out.println("Notas de " + valorNotas[i] + " R$");
                        do {
                            valorInval = 0;
                            aux = scan.nextInt();
                            if (aux >= 0) { // NAO PERMITE QUANTIDADE DE NOTAS NEGATIVA
                                quantidadeNotas[i] = quantidadeNotas[i] + aux;
                                valorInval = 0;
                                saldo = saldo + (aux * valorNotas[i]);
                                valorextrato = valorextrato + (aux * valorNotas[i]);

                            } else {
                                System.out.println("Valor invalido");
                                valorInval = 1;
                            }
                        } while (valorInval == 1);
                    }
                    System.out.println("Este caixa tem: " + saldo + ",00 R$");
                    for (i = 0; i < 5; i++) {
                        texto1 = (quantidadeNotas[i] > 1 || quantidadeNotas[i] == 0) ? " notas" : " nota";
                        System.out.println(+quantidadeNotas[i] + texto1 + " de " + valorNotas[i] + " R$");
                    }
                    //String formattedDate = fmt.format(myDate);
                    extratos[contaExtrato] = new Extrato();
                    extratos[contaExtrato].tipo = "Deposito";
                    extratos[contaExtrato].valor = valorextrato;
                    now = LocalDateTime.now();
                    extratos[contaExtrato].hora = now;
                    contaExtrato++;
                    operacao = -1;
                    break;
                case 1:

                    System.out.println("Saque");
                    System.out.println("Este caixa tem: " + saldo + ",00 R$");
                    for (i = 0; i < 5; i++) {
                        texto1 = (quantidadeNotas[i] > 1 || quantidadeNotas[i] == 0) ? " notas" : " nota";
                        System.out.println(+quantidadeNotas[i] + texto1 + " de " + valorNotas[i] + " R$");
                    }
                    if (saldo > 0) {
                        do {
                            valorInval = 0;
                            System.out.println("Qual valor deseja sacar?");
                            valorSaque = scan.nextInt();
                            System.out.println(+valorSaque + ",00 R$");

                            if (valorSaque > saldo || valorSaque <= 0) {
                                System.out.println("Saldo indisponível");
                                System.out.println("Este caixa tem: " + saldo + ",00 R$");
                                valorInval = -1;
                            } else {
                                restoSaque = valorSaque;
                                for (i = 4; i > -1; i--) {
                                    if (restoSaque >= valorNotas[i] && quantidadeNotas[i] > 0) {
                                        while (quantidadeNotas[i] > 0 && valorSaque >= totalSaque && restoSaque >= valorNotas[i]) {
                                            notasSaque[i]++;
                                            aux = valorNotas[i];
                                            totalSaque = totalSaque + aux;
                                            restoSaque = restoSaque - aux;
                                            quantidadeNotas[i]--;
                                        }
                                    }

                                }
                                if (restoSaque > 0) {
                                    System.out.println(" cedulas indisponiveis para sacar: " + valorSaque + ",00 R$\n");
                                    restoSaque = 0;
                                    totalSaque = 0;
                                    aux = 0;
                                    for (i = 4; i > -1; i--) {
                                        quantidadeNotas[i] = quantidadeNotas[i] + notasSaque[i];
                                        notasSaque[i] = 0;
                                    }
                                    valorInval = -1;
                                } else {
                                    System.out.println("Total sacado: " + totalSaque + ",00 R$");
                                    valorextrato = totalSaque;
                                    restoSaque = 0;
                                    totalSaque = 0;
                                    saldo = 0;
                                    for (i = 0; i < 5; i++) {
                                        texto1 = (notasSaque[i] > 1 || notasSaque[i] == 0) ? " notas" : " nota";
                                        System.out.println(+notasSaque[i] + texto1 + "  de " + valorNotas[i] + ",00 R$");
                                        //quantidadeNotas[i] = quantidadeNotas[i] - notasSaque[i];
                                        saldo = saldo + (quantidadeNotas[i] * valorNotas[i]);
                                        notasSaque[i] = 0;
                                    }
                                    extratos[contaExtrato] = new Extrato();
                                    extratos[contaExtrato].tipo = "Saque";
                                    extratos[contaExtrato].valor = valorextrato;
                                    now = LocalDateTime.now();
                                    extratos[contaExtrato].hora = now;
                                    contaExtrato++;
                                    operacao = -1;

                                }
                            }
                        } while (valorInval == -1);
                        System.out.println("Saldo restante: " + saldo + ",00 R$");
                        for (i = 0; i < 5; i++) {
                            System.out.println(quantidadeNotas[i]);
                        }
                    } else {
                        System.out.println("O Saldo é 0,00 R$");
                    }
                    operacao = -1;
                    break;

                case 2:

                    System.out.println("Extrato: ");
                    for (i = contaExtrato - 1; i > -1; i--) {
                        System.out.println("--------------\n Extrato: \n tipo: " + extratos[i].tipo + "\n valor " + extratos[i].valor + ",00 R$\n hora: " + dtf.format(extratos[i].hora));
                    }
                    operacao = -1;
                    break;
                case 3:
                    System.out.println("Este caixa tem: " + saldo + ",00 R$\n");
                    for (i = 0; i < 5; i++) {
                        texto1 = (quantidadeNotas[i] > 1 || quantidadeNotas[i] == 0) ? " notas" : " nota";
                        System.out.println(+quantidadeNotas[i] + texto1 + " de " + valorNotas[i] + " R$");
                    }
                    operacao = -1;
                    break;
                case 4:
                    saldo = 0;
                    for (i = 0; i < 5; i++) {
                        quantidadeNotas[i] = 0;
                    }
                    System.out.println("Este caixa tem: " + saldo + ",00 R$\n");
                    for (i = 0; i < 5; i++) {
                        texto1 = (quantidadeNotas[i] > 1 || quantidadeNotas[i] == 0) ? " notas" : " nota";
                        System.out.println(+quantidadeNotas[i] + texto1 + " de " + valorNotas[i] + " R$");
                    }
                    operacao = -1;
                    break;
                case 5:

                    System.out.println("Obrigado!");

                    break;
                default:

                    System.out.println("Opcao invalida!");
                    operacao = -1;
            }

        } while (operacao == -1);

    }
}
