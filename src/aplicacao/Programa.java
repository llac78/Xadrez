package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		List<PecaXadrez> capturadas = new ArrayList<>();
		
		while(!partida.getCheckMate()) {
			try {
				UI.limparTela();
				UI.imprimirPartida(partida, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partida.mostrarMovimentosPossiveis(origem);
				UI.limparTela();
				UI.imprimirTabuleiro(partida.getPecas(), movimentosPossiveis);
				
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				
				PecaXadrez pecaCapturada = partida.movimentoXadrez(origem, destino);
				
				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
				
				if(partida.getPecaPromovida() != null) {
					System.out.println("Informe uma peça para promoção (B/C/D/T): ");
					String tipo = sc.nextLine().toUpperCase();
					while(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("D") && !tipo.equals("T")) {
						System.out.println("Informe uma peça para promoção (B/C/D/T): ");
						tipo = sc.nextLine().toUpperCase();
					}
					partida.trocarPecaPromovida(tipo);
				}
				
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
				
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.limparTela();
		UI.imprimirPartida(partida, capturadas);
		
	}

}
