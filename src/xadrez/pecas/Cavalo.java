package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez {

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "C";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		p.atualizarPosicao(posicaoMatriz.getLinha() - 1, posicaoMatriz.getColuna()- 2);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.atualizarPosicao(posicaoMatriz.getLinha() - 2, posicaoMatriz.getColuna() - 1);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.atualizarPosicao(posicaoMatriz.getLinha() - 2, posicaoMatriz.getColuna() + 1);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.atualizarPosicao(posicaoMatriz.getLinha() - 1, posicaoMatriz.getColuna() + 2);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.atualizarPosicao(posicaoMatriz.getLinha() + 1, posicaoMatriz.getColuna() + 2);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.atualizarPosicao(posicaoMatriz.getLinha() + 2, posicaoMatriz.getColuna() + 1);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.atualizarPosicao(posicaoMatriz.getLinha() + 2, posicaoMatriz.getColuna() - 1);
		if(getTabuleiro().isPosicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		p.atualizarPosicao(posicaoMatriz.getLinha() + 1, posicaoMatriz.getColuna() - 2);
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

