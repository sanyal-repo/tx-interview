package com.learnvest.nmlv.lucene;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.IndexableFieldType;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.learnvest.nmlv.model.InstitutionDao;

@Component
public class IndexService {

	private static final String INDEX_DIR = "c:/temp/lucene6index";
	@Autowired
	InstitutionDao repository;

	private static IndexSearcher createSearcher() throws IOException {
		Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
		IndexReader reader = DirectoryReader.open(dir);		
		
		for (int i=0; i<reader.maxDoc(); i++) {
		    if (reader.hasDeletions())
		        continue;

		    Document doc = reader.document(i);
		    doc.getFields().forEach((f)->System.out.println(f.name() + "-->" + doc.get(f.name())));
		    
		}
		
		
		IndexSearcher searcher = new IndexSearcher(reader);
		return searcher;
	}

	private static TopDocs searchByName(String name, IndexSearcher searcher) throws Exception {
		QueryParser qp = new QueryParser("name", new StandardAnalyzer());
		Query nameQuery = qp.parse(name);
		TopDocs hits = searcher.search(nameQuery, 10);
		return hits;
	}
	
	private static TopDocs searchAll(IndexSearcher searcher) throws Exception {
		Query all = new MatchAllDocsQuery();		
		TopDocs hits = searcher.search(all, 10);
		return hits;
	}

    public String[] spellcheck(String aWord) throws Exception {
            	
        SpellChecker spellChecker = new SpellChecker(new RAMDirectory());
        
        Path path = Paths.get("C:\\temp\\dictionary\\words.txt");
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        PlainTextDictionary dict = new PlainTextDictionary(path);        
        
        spellChecker.indexDictionary(dict, config, true);
        
        String wordForSuggestions = aWord; //"hwllo";
        
        int suggestionsNumber = 5;

        String[] suggestions = spellChecker.
            suggestSimilar(wordForSuggestions, suggestionsNumber);

        if (suggestions!=null && suggestions.length>0) {
            for (String word : suggestions) {
                System.out.println("Did you mean:" + word);
            }
        }
        else {
            System.out.println("No suggestions found for word:"+wordForSuggestions);
        }
        
        return suggestions;
            
    }

	public TopDocs searchById(Integer id) throws Exception {
		//QueryParser qp = new QueryParser("name", new StandardAnalyzer());
		//Query idQuery = qp.parse(id.toString());				
		//Query idQuery = builder.createBooleanQuery("id", "13000");		
		///Query all = new MatchAllDocsQuery();
		//Query tq = new TermQuery(new Term("name", "First Community Bank of Godfrey"));
		
		
		IndexSearcher searcher = createSearcher();
		QueryBuilder builder = new QueryBuilder(new WhitespaceAnalyzer());
		List<IndexableField> fnames = searcher.getIndexReader().document(1).getFields();
				//getTermVectors(1).terms("name");
		fnames.forEach((f) -> System.out.println("Listing field name:: " + f.name()));
		Query pq = builder.createPhraseQuery("name", "BankVista"); 
				//"First Community Bank of Godfrey");
		
		//Query query1 = qp.parse("name:Robinhood");

		TopDocs hits = searcher.search(pq, 10);
		//searcher.
		return hits;
	}
	
	public IndexSearcher getIndexSearcher() throws Exception{
		return createSearcher();
	}

	public void testIndex() throws Exception {

		IndexSearcher searcher = createSearcher();

		// Search by ID
		TopDocs foundDocs = searchById(2);

		System.out.println("Total Results :: " + foundDocs.totalHits);

		for (ScoreDoc sd : foundDocs.scoreDocs) {
			Document d = searcher.doc(sd.doc);
			System.out.println(String.format(d.get("name")));
		}

		// Search by firstName
		TopDocs foundDocs2 = searchByName("The Cottonport Bank", searcher);

		System.out.println("Total Results :: " + foundDocs2.totalHits);

		for (ScoreDoc sd : foundDocs2.scoreDocs) {
			Document d = searcher.doc(sd.doc);
			System.out.println(String.format(d.get("id")));
		}
		
		
		
		searcher.getIndexReader().close();

	}

}
