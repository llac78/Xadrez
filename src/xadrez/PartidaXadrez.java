package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	private Integer vez;
	private Cor jogadorAtual;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		vez = 1;
		jogadorAtual = Cor.BRANCA;
		setupInicial();
	}
	
	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public Integer getVez() {
		return vez;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
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
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = movimentar(origem, destino);
		trocarVez();
		
		return (PecaXadrez) pecaCapturada;
	}
	
	private Peca movimentar(Posicao origem, Posicao destino) {

		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		return pecaCapturada;
	}
	
	public boolean[][] mostrarMovimentosPossiveis(PosicaoXadrez origem){
		Posicao posicao = origem.toPosicao();
		validarPosicaoInicial(posicao);
		
		return tabuleiro.obterPeca(posicao).movimentosPossiveis();
	}

	private void validarPosicaoInicial(Posicao origem) {

		if(!tabuleiro.temUmaPecaNaPosicao(origem)) {
			throw new XadrezException("Não há peça na posição de origem!");
		}
		
		if(jogadorAtual != ((PecaXadrez)tabuleiro.obterPeca(origem)).getCor() ) {
			throw new XadrezException("A peça escolhida não é sua!");
		}
		
		if(!tabuleiro.obterPeca(origem).existeMovimentosPossiveis()) {
			throw new XadrezException("Não há movimentos possíveis para a peça escolhida!");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {

		if(!tabuleiro.obterPeca(origem).movimentoPossivel(destino)) {
			throw new XadrezException("A peça escolhida não pode ser movimentada para esta posição!");
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
	
	private void trocarVez() {
		vez++;
		jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
}
