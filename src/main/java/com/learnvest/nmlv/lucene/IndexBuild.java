package com.learnvest.nmlv.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.learnvest.nmlv.model.InstitutionDao;

@Component
public class IndexBuild {

	private static final String INDEX_DIR = "c:/temp/lucene6index";
	@Autowired
	InstitutionDao repository;
	private static IndexWriter createWriter() throws IOException {
		FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
		IndexWriterConfig config = new IndexWriterConfig(new WhitespaceAnalyzer());
		IndexWriter writer = new IndexWriter(dir, config);
		return writer;
	}
	
	public void createIndex() throws Exception{

		IndexWriter writer = createWriter();

		ArrayList<Document> dl = new ArrayList<Document>();
		repository.findAll().stream().forEach((i) -> {
			Document document = new Document();
			FieldType ft = new FieldType();
			ft.setStored(true);
			document.add(new Field("id", i.getId().toString(), ft));
			document.add(new Field("name", i.getName(), ft));
			document.add(new Field("display_name", i.getDisplayName(), ft));
			dl.add(document);
		});

		try {
			writer.deleteAll();
			writer.addDocuments(dl);
			writer.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}

	}
}
