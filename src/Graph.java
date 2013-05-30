import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;


public class Graph 
{
	private int[][] verticesGraph;
	private final int MAX_NODES = 26;
	private char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	public Graph()
	{
		verticesGraph = new int[MAX_NODES][MAX_NODES];
		for(int i = 0; i < verticesGraph.length; i++)
		{
			for(int j = 0; j < verticesGraph.length; j++)
			{
				verticesGraph[i][j] = 0;
			}
		}
	}
	
	public void build(String graph)
	{
		dealWithTokens(graph);
	}
	
	private void dealWithTokens(String graph)
	{
		StringTokenizer graphTokens = new StringTokenizer(graph);
		if(graphTokens.countTokens() == 0)
		{
			System.out.println("Try adding some vertices to connect.");
		}
		else if(graphTokens.countTokens() % 2 == 0)
		{
			while(graphTokens.hasMoreTokens())
			{
				String nextToken = graphTokens.nextToken();
				String tokenAfterNext = graphTokens.nextToken();
				if(isUpper(nextToken) && isUpper(tokenAfterNext))
				{
					connectTheTwoTokens(nextToken, tokenAfterNext);
				}
			}
		}
	}
	
	private boolean isUpper(String nextToken)
	{
		boolean decider = false;
		for(char c : nextToken.toCharArray())
		{
			if(Character.isUpperCase(c))
			{
				decider = true;
				break;
			}
		}
		return decider;
	}
	
	private void connectTheTwoTokens(String firstToken, String nextToken)
	{
		int firstVertexIntRep = firstTokenIntRep(firstToken);
		connectToSecondToken(nextToken, firstVertexIntRep);
	}
	
	private int firstTokenIntRep(String firstToken)
	{
		char[] token1 = firstToken.toCharArray();
		char firstVertex = token1[0];
		int vertexIntRep = -1; //should never return -1 with proper input
		for(int j = 0; j < alphabet.length; j++)
		{
			if(firstVertex == alphabet[j])
			{
				vertexIntRep = j;
			}
		}
		return vertexIntRep;
	}
	
	
	private void connectToSecondToken(String nextToken, int firstVertex)
	{
		ArrayList<Integer> verticesIndices = nextTokenIndices(nextToken);
		for(int i = 0; i < verticesIndices.size(); i++)
		{
			verticesGraph[firstVertex][verticesIndices.get(i)] = 1;
		}
	}
	
	private ArrayList<Integer> nextTokenIndices(String nextToken)
	{
		char[] connectors = nextToken.toCharArray();
		ArrayList<Integer> verticesIndices = new ArrayList<Integer>(); 
		for(char c : connectors)
		{
			for(int i = 0; i < alphabet.length; i++)
			{
				if(c == alphabet[i])
				{
					verticesIndices.add(i);
				}
			}
		}
		return verticesIndices;
	}
	
	public String toString()
	{
		String ordered = "{";
		/*String unordered = unAlphabetizedString();
		StringTokenizer tokens = new StringTokenizer(unordered, "],");
		ordered += alphabetize(tokens);*/
		ordered += checkOneDirection();
		ordered += checkOtherDirection();
		ordered += "}";
		return ordered;
	}
	
	private String alphabetize(StringTokenizer tokens)
	{
		String ordered = "";
		StringTokenizer tempTokens = tokens;
		List<Character> compareKey = new ArrayList<Character>();
		while(tokens.hasMoreTokens())
		{
			String nextToken = tokens.nextToken();
			char[] nextTokenArray = nextToken.toCharArray();
			compareKey.add(nextTokenArray[0]);
		}
		Collections.sort(compareKey);
		int counter = 0;
		while(tempTokens.hasMoreTokens())
		{
			String tempNextToken = tempTokens.nextToken();
			char[] tempNextTokenArray = tempNextToken.toCharArray();
			while(counter < compareKey.size())
			{
				if(tempNextTokenArray[0] == compareKey.get(counter))
				{
					ordered += tempNextToken;
					counter++;
				}
			}
		}
		return ordered;
	}
	
	private String unAlphabetizedString()
	{
		String strGraph = "";
		strGraph += checkOneDirection();
		strGraph += checkOtherDirection();
		//strGraph += "}";
		return strGraph;
	}
	
	private String checkOneDirection()
	{
		String strGraph = "";
		for(int row = 0; row < verticesGraph.length; row++)
		{
			int counter = 0;
			int bracketChecker = 0;
			for(int col = 0; col < verticesGraph.length; col++)
			{
				if(verticesGraph[row][col] == 1)
				{
					if(counter < 1)
					{
						strGraph += alphabet[row] + "=[";
						counter++;
					}
					strGraph += alphabet[col] + ", ";
					bracketChecker++;
				}
			}
			if(bracketChecker > 0)
			{
				strGraph += "], ";
			}
		}
		return strGraph;
	}
	
	private String checkOtherDirection()
	{
		String strGraph = "";
		for(int col = 0; col < verticesGraph.length; col++)
		{
			int counter = 0;
			for(int row = 0; row < verticesGraph.length; row++)
			{
				if(verticesGraph[row][col] == 1)
				{
					if(counter < 1)
					{
						strGraph += alphabet[col] + "=[";
						counter++;
					}
					strGraph += alphabet[row] + "], ";
				}
			}
		}
		return strGraph;
	}
	
	public static void main(String[] args)
	{
		Graph myGraph = new Graph();
		myGraph.build("A BCD E FGH");
		System.out.println(myGraph.checkOneDirection());
		System.out.println(myGraph.checkOtherDirection());
		System.out.println(myGraph.toString());
	}
	
}
