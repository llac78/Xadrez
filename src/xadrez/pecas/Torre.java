package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] movimentosPossiveis() {

		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		// acima
		p.atualizarPosicao(posicaoMatriz.getLinha() - 1, posicaoMatriz.getColuna());
		while (getTabuleiro().isPosicaoExistente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().isPosicaoExistente(p) && existePecaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		// esquerda
		p.atualizarPosicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() - 1);
		while (getTabuleiro().isPosicaoExistente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().isPosicaoExistente(p) && existePecaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		// direita
		p.atualizarPosicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() + 1);
		while (getTabuleiro().isPosicaoExistente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().isPosicaoExistente(p) && existePecaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		// abaixo
		p.atualizarPosicao(posicaoMatriz.getLinha() + 1, posicaoMatriz.getColuna());
		while (getTabuleiro().isPosicaoExistente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().isPosicaoExistente(p) && existePecaAdversaria(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		return matriz;
	}
}
