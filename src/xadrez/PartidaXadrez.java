package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		setupInicial();
	}
	
	public PecaXadrez[][] getPecas(){
		
		PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaXadrez) tabuleiro.obterPeca(i, j);
			}
			
		}
		return matriz;
	}
	
	public PecaXadrez movimentoXadrez(PosicaoXadrez posicaoInicial, PosicaoXadrez posicaoFinal) {
		
		Posicao origem = posicaoInicial.toPosicao();
		Posicao destino = posicaoFinal.toPosicao();
		validarPosicaoInicial(origem);
		Peca pecaCapturada = movimentar(origem, destino);
		
		return (PecaXadrez) pecaCapturada;
	}
	
	private Peca movimentar(Posicao origem, Posicao destino) {

		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		return pecaCapturada;
	}

	private void validarPosicaoInicial(Posicao origem) {

		if(!tabuleiro.isPosicaoExistente(origem)) {
			throw new XadrezException("Não há peça na posição de origem!");
		}
	}

	private void setupInicial() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('a', 4, new Rei(tabuleiro, Cor.PRETA));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('g', 7, new Rei(tabuleiro, Cor.BRANCA));
		}
	
	private void colocarNovaPeca(char coluna, Integer linha, PecaXadrez pecaXadrez) {
		tabuleiro.colocarPeca(pecaXadrez, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
}
