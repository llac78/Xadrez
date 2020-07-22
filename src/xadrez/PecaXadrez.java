package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Cor cor;
	private int contadorMovimentos;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

	public int getContadorMovimentos() {
		return contadorMovimentos;
	}

	protected Boolean existePecaAdversaria(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().obterPeca(posicao);

		return p != null && p.getCor() != cor;
	}

	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.toPosicaoXadrez(posicaoMatriz);
	}

	public void incrementarContador() {
		contadorMovimentos++;
	}

	public void decrementarContador() {
		contadorMovimentos--;
	}

}
