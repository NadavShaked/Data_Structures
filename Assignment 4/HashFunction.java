public class HashFunction {
	private int a;
	private int b;
	private int p;
	
	public HashFunction(int a, int b, int p) {
		this.a = a;
		this.b = b;
		this.p = p;
	}
	
	public int hashSolution(int k, int size) {
		return ((a%p)*(k%p)+(b%p))%size;
	}
}
