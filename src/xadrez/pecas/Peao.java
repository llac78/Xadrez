package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {
	
	private PartidaXadrez partida;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partida) {
		super(tabuleiro, cor);
		
		this.partida = partida;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		if (getCor() == Cor.BRANCA) {
			p.atualizarPosicao(posicaoMatriz.getLinha() - 1, posicaoMatriz.getColuna());
			if (getTabuleiro().isPosicaoExistente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.atualizarPosicao(posicaoMatriz.getLinha() - 2, posicaoMatriz.getColuna());
			Posicao p2 = new Posicao(posicaoMatriz.getLinha() - 1, posicaoMatriz.getColuna());
			if (getTabuleiro().isPosicaoExistente(p) && !getTabuleiro().temUmaPecaNaPosicao(p) && 
				getTabuleiro().isPosicaoExistente(p2) 
				&& !getTabuleiro().temUmaPecaNaPosicao(p2) 
				&& getContadorMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.atualizarPosicao(posicaoMatriz.getLinha() - 1, posicaoMatriz.getColuna() - 1);
			if (getTabuleiro().isPosicaoExistente(p) && existePecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}			
			p.atualizarPosicao(posicaoMatriz.getLinha() - 1, posicaoMatriz.getColuna() + 1);
			if (getTabuleiro().isPosicaoExistente(p) && existePecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}	
			
			//enPassant
			if(posicaoMatriz.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() - 1);
				if(getTabuleiro().isPosicaoExistente(esquerda) && existePecaAdversaria(esquerda) && 
						getTabuleiro().obterPeca(esquerda) == partida.getEnPassantVulneravel()) {
					matriz[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				
				Posicao direita = new Posicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() + 1);
				if(getTabuleiro().isPosicaoExistente(direita) && existePecaAdversaria(direita) && 
						getTabuleiro().obterPeca(esquerda) == partida.getEnPassantVulneravel()) {
					matriz[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}
		}
		else {
			p.atualizarPosicao(posicaoMatriz.getLinha() + 1, posicaoMatriz.getColuna());
			if (getTabuleiro().isPosicaoExistente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.atualizarPosicao(posicaoMatriz.getLinha() + 2, posicaoMatriz.getColuna());
			Posicao p2 = new Posicao(posicaoMatriz.getLinha() + 1, posicaoMatriz.getColuna());
			if (getTabuleiro().isPosicaoExistente(p) && !getTabuleiro().temUmaPecaNaPosicao(p) && 
				getTabuleiro().isPosicaoExistente(p2) && !getTabuleiro().temUmaPecaNaPosicao(p2) && getContadorMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.atualizarPosicao(posicaoMatriz.getLinha() + 1, posicaoMatriz.getColuna() - 1);
			if (getTabuleiro().isPosicaoExistente(p) && existePecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}			
			p.atualizarPosicao(posicaoMatriz.getLinha() + 1, posicaoMatriz.getColuna() + 1);
			if (getTabuleiro().isPosicaoExistente(p) && existePecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			//enPassant
			if(posicaoMatriz.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() - 1);
				if(getTabuleiro().isPosicaoExistente(esquerda) && existePecaAdversaria(esquerda) && 
						getTabuleiro().obterPeca(esquerda) == partida.getEnPassantVulneravel()) {
					matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				
				Posicao direita = new Posicao(posicaoMatriz.getLinha(), posicaoMatriz.getColuna() + 1);
				if(getTabuleiro().isPosicaoExistente(direita) && existePecaAdversaria(direita) && 
						getTabuleiro().obterPeca(esquerda) == partida.getEnPassantVulneravel()) {
					matriz[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
			
		}
		return matriz;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
