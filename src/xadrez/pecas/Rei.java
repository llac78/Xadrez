package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {
	
	private PartidaXadrez partida;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partida) {
		super(tabuleiro, cor);
		this.partida = partida;
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
	
		//Roque
		if (getContadorMovimentos() == 0 && !partida.getCheck()) {
			// pequeno
			Posicao posTorre1 = new Posicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() + 3);
			if (testarTorreRoque(posTorre1)) {
				Posicao p1 = new Posicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() + 1);
				Posicao p2 = new Posicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() + 2);
				if (getTabuleiro().obterPeca(p1) == null && getTabuleiro().obterPeca(p2) == null) {
					matriz[posicaoMatriz.getLinha()][posicaoMatriz.getColuna() + 2] = true;
				}
			}
			// grande
			Posicao posTorre2 = new Posicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() - 4);
			if (testarTorreRoque(posTorre2)) {
				Posicao p1 = new Posicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() - 1);
				Posicao p2 = new Posicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() - 2);
				Posicao p3 = new Posicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() - 3);
				if (getTabuleiro().obterPeca(p1) == null && getTabuleiro().obterPeca(p2) == null && getTabuleiro().obterPeca(p3) == null) {
					matriz[posicaoMatriz.getLinha()][posicaoMatriz.getColuna() - 2] = true;
				}
			}
		}
		
		
		return matriz;
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez peca = (PecaXadrez)getTabuleiro().obterPeca(posicao);
		
		return peca == null || peca.getCor() != getCor();
	}
	
	private boolean testarTorreRoque(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().obterPeca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorMovimentos() == 0;
	}

	

}
