import java.util.*;

public class C
{
	String[] banWordList = {"why", "you", "are", "oh", "owe", "eye", "sea", "see", "bea", "be", "bee", "for", "four", "to", "too", "two", "one", "won", "and", "at"};
	char[] replList = {'y', 'u', 'r', 'o', 'o', 'i', 'c', 'c', 'b', 'b', 'b', '4', '4', '2', '2', '2', '1', '1', '&', '@'};
	
	Scanner in = new Scanner(System.in);
	String output = "";
	
	int maxCheckedIndex = 0;
	int usedKey, usedVal;
	
	C()
	{
		usedVal = usedKey = -1;
		
		int amountOfLines = in.nextInt();
		in.nextLine();
		for(int lineIndex = 0; lineIndex < amountOfLines; lineIndex++)
		{
			// FOR EACH LINE
			String line = in.nextLine();
			
			for(String word : line.split(" "))
			{
				while(maxCheckedIndex != -1)
				{
					word = fixWord(word, maxCheckedIndex);
				}
				maxCheckedIndex = 0;
				
				System.out.print(word + " ");
			}
			
			System.out.print("\n");
		}
		
		System.out.println(output);
	}
	
	public int numOfErrors(String word)
	{
		int counter = 0;
		for(int i = 0; i < banWordList.length; i++)
		{
			if(word.toLowerCase().contains(banWordList[i]))
				counter++;
		}
		
		return counter;
	}
	
	public String fixWord(String word, int startingIndex)
	{
		AbstractMap.SimpleEntry<Integer, Integer> second, first;
		second = first = getFirstIndexFrom(word, startingIndex);
		
		if(first.getValue() != -1)
			second = getFirstIndexFrom(word, startingIndex);
		
		if(first.getKey() == Integer.MAX_VALUE) // IF THERE ARE NONE, EXIT
		{
			return word;
		}
		else 
		{
			if(second.getKey() == Integer.MAX_VALUE)
			{
				int firstIndex = first.getKey();
				int lenOfFirstWord = banWordList[first.getValue()].length();
				
				String firstWord = word.substring(firstIndex, firstIndex + lenOfFirstWord);
				
				word = replace(word, firstWord, first);
			}
			else
			{
				// IF THERE IS A SECOND INDEX
				// GET INFO
				int firstIndex = first.getKey();
				int secondIndex = second.getKey();
				
				int lenOfFirstWord = banWordList[first.getValue()].length();
				int lenOfSecondWord = banWordList[second.getValue()].length();
				
				String firstWord = word.substring(firstIndex, firstIndex + lenOfFirstWord);
				String secondWord = word.substring(secondIndex, secondIndex + lenOfSecondWord);
				
				int firstMin = firstIndex;
				int firstMax = firstIndex + lenOfFirstWord;
				int secondMin = secondIndex;
				int secondMax = secondIndex + lenOfSecondWord;
				
				if(firstIndex == secondIndex)
				{
					// If they are at the same place, replace the longer word
					if(lenOfFirstWord >= lenOfSecondWord)
					{
						word = replace(word, firstWord, first);
					}
					else
					{
						word = replace(word, secondWord, second);
					}
				}
				else if( (firstMin >= secondMin && firstMin <= secondMax) || (secondMin >= firstMin && secondMin <= firstMax) )
				{
					word = replace(word, firstWord, first);
				}
				else
				{
					// IF THEY ARE SEPERATE
					word = replace(word, firstWord, first);
					word = replace(word, secondWord, second);
				}
			}
		}

		usedVal = usedKey = -1;
		
		return word;
	}
	
	public String replace(String word, String wordToReplace, AbstractMap.SimpleEntry<Integer, Integer> se)
	{
		if(Character.isUpperCase(wordToReplace.charAt(0)))
		{
			// MAKE REPLACER CAPITAL
			StringBuilder replacerSB = new StringBuilder(replList[se.getValue()] + "");
			replacerSB.setCharAt(0, Character.toUpperCase(replList[se.getValue()]));
			
			String replacer = replacerSB.toString();
			
			return word.replace(wordToReplace, replacer);
		}
		else
		{
			return word.replace(wordToReplace, replList[se.getValue()] + "");
		}
	}
	
	public AbstractMap.SimpleEntry<Integer, Integer> getFirstIndexFrom(String word, int index)
	{		
		int firstIndex = Integer.MAX_VALUE;
		int firstBadIndex = -1;
		
		for(int i = 0; i < banWordList.length; i++)
		{
			int btIndex = word.toLowerCase().indexOf(banWordList[i], index);
			
			if(usedKey != btIndex || usedVal != i)
			{
				if(btIndex != -1 && btIndex < firstIndex)
				{
					firstIndex = btIndex;
					firstBadIndex = i;
					maxCheckedIndex = btIndex - banWordList[i].length();
				}
			}
			
		}
		
		if(firstIndex == Integer.MAX_VALUE)
			maxCheckedIndex = -1;
		
		usedKey = firstIndex;
		usedVal = firstBadIndex;
		
		return new AbstractMap.SimpleEntry<Integer, Integer>(firstIndex, firstBadIndex);
	}
	
	public static void main(String[] args)
	{
		new C();
	}
}