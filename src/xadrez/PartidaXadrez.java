package xadrez;

import java.awt.Color;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	private Integer vez;
	private Cor jogadorAtual;
	
	private List<Peca> pecasTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	private boolean check;

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
	
	public boolean getCheck() {
		return check;
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
		
		if(testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezException("Voc� n�o pode se colocar em check!");
		}
		
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		trocarVez();
		
		return (PecaXadrez) pecaCapturada;
	}
	
	private Peca movimentar(Posicao origem, Posicao destino) {

		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		if(pecaCapturada != null) {
			pecasTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		return pecaCapturada;
	}
	
	public boolean[][] mostrarMovimentosPossiveis(PosicaoXadrez origem){
		Posicao posicao = origem.toPosicao();
		validarPosicaoInicial(posicao);
		
		return tabuleiro.obterPeca(posicao).movimentosPossiveis();
	}

	private void validarPosicaoInicial(Posicao origem) {

		if(!tabuleiro.temUmaPecaNaPosicao(origem)) {
			throw new XadrezException("N�o h� pe�a na posi��o de origem!");
		}
		
		if(jogadorAtual != ((PecaXadrez)tabuleiro.obterPeca(origem)).getCor() ) {
			throw new XadrezException("A pe�a escolhida n�o � sua!");
		}
		
		if(!tabuleiro.obterPeca(origem).existeMovimentosPossiveis()) {
			throw new XadrezException("N�o h� movimentos poss�veis para a pe�a escolhida!");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {

		if(!tabuleiro.obterPeca(origem).movimentoPossivel(destino)) {
			throw new XadrezException("A pe�a escolhida n�o pode ser movimentada para esta posi��o!");
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
		pecasTabuleiro.add(pecaXadrez);
	}
	
	private void trocarVez() {
		vez++;
		jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturada) {
		
		Peca p = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, origem);
		
		if(capturada != null) {
			tabuleiro.colocarPeca(capturada, destino);
			pecasCapturadas.remove(capturada);
			pecasTabuleiro.add(capturada);
		}
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private PecaXadrez buscarRei(Cor cor) {
		List<Peca> lista = pecasTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		
		for (Peca peca : lista) {
			if(peca instanceof Rei) {
				return (PecaXadrez) peca;
			}
		}
		throw new IllegalStateException("N�o h� rei da cor " + cor + " no tabuleiro!");
	}
	
	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = buscarRei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> listaOponentes = pecasTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
	
		for (Peca peca : listaOponentes) {
			boolean[][] matriz = peca.movimentosPossiveis();
			if(matriz[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
}
