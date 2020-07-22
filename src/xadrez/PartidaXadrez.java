package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	private Integer vez;
	private Cor jogadorAtual;

	private List<Peca> pecasTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	private boolean check;
	private boolean checkMate;

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

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecaXadrez[][] getPecas() {

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

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezException("Você não pode se colocar em check!");
		}

		check = (testarCheck(oponente(jogadorAtual))) ? true : false;

		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			trocarVez();
		}

		return (PecaXadrez) pecaCapturada;
	}

	private Peca movimentar(Posicao origem, Posicao destino) {

		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
		p.incrementarContador();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		return pecaCapturada;
	}

	public boolean[][] mostrarMovimentosPossiveis(PosicaoXadrez origem) {
		Posicao posicao = origem.toPosicao();
		validarPosicaoInicial(posicao);

		return tabuleiro.obterPeca(posicao).movimentosPossiveis();
	}

	private void validarPosicaoInicial(Posicao origem) {

		if (!tabuleiro.temUmaPecaNaPosicao(origem)) {
			throw new XadrezException("Não há peça na posição de origem!");
		}

		if (jogadorAtual != ((PecaXadrez) tabuleiro.obterPeca(origem)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua!");
		}

		if (!tabuleiro.obterPeca(origem).existeMovimentosPossiveis()) {
			throw new XadrezException("Não há movimentos possíveis para a peça escolhida!");
		}
	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {

		if (!tabuleiro.obterPeca(origem).movimentoPossivel(destino)) {
			throw new XadrezException("A peça escolhida não pode ser movimentada para esta posição!");
		}
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

		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
		p.decrementarContador();
		tabuleiro.colocarPeca(p, origem);

		if (capturada != null) {
			tabuleiro.colocarPeca(capturada, destino);
			pecasCapturadas.remove(capturada);
			pecasTabuleiro.add(capturada);
		}
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}

	private PecaXadrez buscarRei(Cor cor) {
		List<Peca> lista = pecasTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());

		for (Peca peca : lista) {
			if (peca instanceof Rei) {
				return (PecaXadrez) peca;
			}
		}
		throw new IllegalStateException("Não há rei da cor " + cor + " no tabuleiro!");
	}

	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = buscarRei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> listaOponentes = pecasTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());

		for (Peca peca : listaOponentes) {
			boolean[][] matriz = peca.movimentosPossiveis();
			if (matriz[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> lista = pecasTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca peca : lista) {
			boolean[][] matriz = peca.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Posicao origem = ((PecaXadrez) peca).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = movimentar(origem, destino);
						boolean testeCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void setupInicial() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCA));
		// colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		// colocarNovaPeca('c', 1, new Bispo( tabuleiro, Cor.BRANCA));
		// colocarNovaPeca('d', 1, new Dama( tabuleiro, Cor.BRANCA));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCA));
		// colocarNovaPeca('f', 1, new Bispo( tabuleiro, Cor.BRANCA));
		// colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCA));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETA));
		//colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
		//colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETA));
		//colocarNovaPeca('d', 8, new Dama(tabuleiro, Cor.PRETA));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETA));
		//colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETA));
		//colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETA));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETA));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETA));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETA));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETA));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETA));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETA));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETA));
	}

}
