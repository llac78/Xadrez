package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//acima
		p.atualizarPosicao(posicaoMatriz.getLinha() - 1, posicaoMatriz.getColuna());
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//abaixo
		p.atualizarPosicao(posicaoMatriz.getLinha() + 1, posicaoMatriz.getColuna());
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda
		p.atualizarPosicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() - 1);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//direita
		p.atualizarPosicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() + 1);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//noroeste
		p.atualizarPosicao(posicaoMatriz.getLinha() - 1, posicaoMatriz.getColuna() - 1);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//nordeste
		p.atualizarPosicao(posicaoMatriz.getLinha() - 1, posicaoMatriz.getColuna() + 1);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudoeste
		p.atualizarPosicao(posicaoMatriz.getLinha() + 1, posicaoMatriz.getColuna() - 1);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudeste
		p.atualizarPosicao(posicaoMatriz.getLinha() + 1, posicaoMatriz.getColuna() + 1);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
	
		
		
		return matriz;
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez peca = (PecaXadrez)getTabuleiro().obterPeca(posicao);
		
		return peca == null || peca.getCor() != getCor();
	}

}
