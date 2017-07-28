/*
     RoadRunner - an automatic wrapper generation system for Web data sources
     Copyright (C) 2003  Valter Crescenzi - crescenz@dia.uniroma3.it

     This program is  free software;  you can  redistribute it and/or
     modify it  under the terms  of the GNU General Public License as
     published by  the Free Software Foundation;  either version 2 of
     the License, or (at your option) any later version.

     This program is distributed in the hope that it  will be useful,
     but  WITHOUT ANY WARRANTY;  without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
     General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with this program; if not, write to the:

     Free Software Foundation, Inc.,
     59 Temple Place, Suite 330,
     Boston, MA 02111-1307 USA

     ----

     RoadRunner - un sistema per la generazione automatica di wrapper su sorgenti Web
     Copyright (C) 2003  Valter Crescenzi - crescenz@dia.uniroma3.it

     Questo  programma �  software libero; �  lecito redistribuirlo  o
     modificarlo secondo i termini della Licenza Pubblica Generica GNU
     come � pubblicata dalla Free Software Foundation; o la versione 2
     della licenza o (a propria scelta) una versione successiva.

     Questo programma  � distribuito nella speranza che sia  utile, ma
     SENZA  ALCUNA GARANZIA;  senza neppure la  garanzia implicita  di
     NEGOZIABILIT�  o di  APPLICABILIT� PER  UN PARTICOLARE  SCOPO. Si
     veda la Licenza Pubblica Generica GNU per avere maggiori dettagli.

     Questo  programma deve  essere  distribuito assieme  ad una copia
     della Licenza Pubblica Generica GNU; in caso contrario, se ne pu�
     ottenere  una scrivendo  alla:

     Free  Software Foundation, Inc.,
     59 Temple Place, Suite 330,
     Boston, MA 02111-1307 USA

*/
/**
 * BrowserMarshall.java
 *   This class produces a set of browsable files in the output directory.
 *
 * +---------+--------+
 * | Wrappers  |                |
 * +--------+ Instances |
 * | Samples   |                |
 * +--------+--------+
 *
 * RoadRunner/output/
 * |  (for wrappers called 'w<N>')
 * |
 * +--wResults.html <- RoadRunnerResults.html (link to ) (3 frames)
 * +--wWrappersIndex.html (contains the mapping: wrappers -> samples, with dl)
 * +--wDataSet<N>.xml
 *
 *RoadRunner/output/.style
 * |
 * +--data.xsl
 *+-- index.xsl
 * Created on 28 aprile 2003, 15.18
 * @author  Valter Crescenzi
 */

package com.wolf_datamining.autoextracting.roadrunner.marshall;


import java.io.*;
import java.util.*;
import java.util.logging.*;
import java.text.DecimalFormat;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.wolf_datamining.autoextracting.roadrunner.ast.ASTAnd;
import com.wolf_datamining.autoextracting.roadrunner.ast.ASTDecoder;
import com.wolf_datamining.autoextracting.roadrunner.ast.ASTEncoder;
import com.wolf_datamining.autoextracting.roadrunner.ast.Expression;
import com.wolf_datamining.autoextracting.roadrunner.ast.SimpleNode;
import com.wolf_datamining.autoextracting.roadrunner.config.Config;
import com.wolf_datamining.autoextracting.roadrunner.config.Constants;
import com.wolf_datamining.autoextracting.roadrunner.config.Preferences;
import com.wolf_datamining.autoextracting.roadrunner.engine.MDLEvaluator;
import com.wolf_datamining.autoextracting.roadrunner.parser.BindingException;
import com.wolf_datamining.autoextracting.roadrunner.util.DOMLoader;
import com.wolf_datamining.autoextracting.roadrunner.util.Indenter;
import com.wolf_datamining.autoextracting.roadrunner.util.Util;

import dataming.autoextracting.roadrunner.Instance;
import dataming.autoextracting.roadrunner.Sample;
import dataming.autoextracting.roadrunner.Wrapper;
import dataming.autoextracting.roadrunner.WrappersRepository;


public class BrowserMarshall implements MarshallConstants {
    
    static private Logger log = Logger.getLogger(BrowserMarshall.class.getName());
    
    static final private String RESULTS_FILENAME = "results.html";
    static final private String WRPINDEX_SUFFIX  = "WrappersIndex";
    static final private String DATASET_SUFFIX   = "_DataSet";
    static final private String STYLE_DIRNAME    = ".style";
    
    static final private DecimalFormat formatter = new DecimalFormat("00"); //to format wrapper filename
    
    private File getResultsFile() throws FileNotFoundException {
        return getFileInWrapperOutDir(RESULTS_FILENAME);
    }
    private File getWrapperFile(Wrapper w) throws FileNotFoundException {
        return getFileInWrapperOutDir(w.getName()+formatter.format(w.getId())+".xml");
    }
    private File getIndexFile() throws FileNotFoundException {
        return getFileInWrapperOutDir(this.name+WRPINDEX_SUFFIX+".xml");
    }
    private File getDataSetFile(Wrapper wrapper) throws FileNotFoundException {
        return getFileInWrapperOutDir(wrapper.getName()+wrapper.getId()+DATASET_SUFFIX+".xml");
    }
    private File getRelativeIndexStyleFile() throws FileNotFoundException {
        return new File(".."+File.separatorChar+STYLE_DIRNAME+File.separatorChar+indexStyle.getName());
    }    
    private File getRelativeDataSetStyleFile() throws FileNotFoundException {
        return new File(".."+File.separatorChar+STYLE_DIRNAME+File.separatorChar+datasetStyle.getName());
    }
    private File getFileInWrapperOutDir(String filename) throws FileNotFoundException {
        return Config.getFileInOutputDir(this.name+File.separator+filename);
    }
    private File getFileInStylesDir(String filename) throws FileNotFoundException {
        return Config.getFileInOutputDir(STYLE_DIRNAME+File.separator+filename);
    }
    
    /** Creates a new instance of BrowserMarshall */
    private WrappersRepository repository;
    private String name;       // a name for output wrappers
    
    private File datasetStyle; // stylesheet for dataset files
    private File indexStyle;   // stylesheet for index files
    
    private File distDatasetStyle; // stylesheet from RoadRunner Distribution
    private File distIndexStyle;   // stylesheet from RoadRunner Distribution
    
    public BrowserMarshall(WrappersRepository rep, String name) throws IOException {
        this.repository = rep;
        this.name = name;
        this.distDatasetStyle = Util.searchInRRHOME(Config.getPrefs().getString(Constants.DATASETSTYLE));
        this.datasetStyle = getFileInStylesDir(distDatasetStyle.getName());
        this.distIndexStyle = Util.searchInRRHOME(Config.getPrefs().getString(Constants.INDEXSTYLE));
        this.indexStyle = getFileInStylesDir(distIndexStyle.getName());
    }
    
    public void marshall() throws IOException, BindingException {
        createOutputDirs();
        writeWrapperFiles();
        writeDataSetFiles();
        writeResultFile();
        writeIndexFile();
    }
    
    private void createOutputDirs() throws IOException {
        // create a directory named after the wrapper to contain the results
        File resultsdir = Config.getFileInOutputDir(this.name);
        resultsdir.mkdir();
        // create a sub-directory for style files
        File styledir = Config.getFileInOutputDir(STYLE_DIRNAME);
        styledir.mkdir();
        copyFreshStyleFiles();
    }
    
    private void copyFreshStyleFiles() throws IOException {
        if (!datasetStyle.exists() || datasetStyle.lastModified()<distDatasetStyle.lastModified()) {
            Util.copyFile(distDatasetStyle, datasetStyle);
        }
        if (!indexStyle.exists() || indexStyle.lastModified()<distIndexStyle.lastModified()) {
            Util.copyFile(distIndexStyle, indexStyle);
        }
    }
    
    private void writeResultFile() throws IOException {
        Wrapper first = (Wrapper)this.repository.getWrappers().iterator().next();
//        PrintWriter out = new PrintWriter(new FileWriter(getResultsFile()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getResultsFile()),"utf-8"));
        out.println("<!DOCTYPE HTML PUBLIC \"-//W)3C//DTD HTML 4.01 Frameset//EN\"");
        out.print("   \"http://www.w3.org/TR/html4/frameset.dtd\">");
        out.println("<HTML><HEAD><TITLE>The RoadRunner Project</TITLE></HEAD>");
        out.println("<FRAMESET cols=\"20%, 80%\">");
        out.println("  <FRAME src=\""+getIndexFile().getName()+"\" name=\"index\"/>");
        out.println("  <FRAME src=\""+getDataSetFile(first).getName()+"\" name=\"dataset\" >");
        out.println("  <NOFRAMES>");
        out.println("      <P>Need frames to display results</P>");
        out.println("  </NOFRAMES>");
        out.println("</FRAMESET>");
        out.println("</HTML>");
        out.close();
    }
    
    private void writeIndexFile() throws IOException {
        Indenter ind = new Indenter(false);
        DecimalFormat formatter = new DecimalFormat(".##%");
//        PrintWriter out = new PrintWriter(new FileWriter(getIndexFile()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getIndexFile()),"utf-8"));
        out.println("<?xml version='1.0' encoding=\"UTF-8\"?>");
        out.println("<?xml-stylesheet href=\""+getRelativeIndexStyleFile()+"\" type=\"text/xsl\"?>");
        out.println(ind+"<"+INDEX+">");
        ind.inc();
        Iterator it = this.repository.getWrappers().iterator();
        while (it.hasNext()) {
            Wrapper wrapper = (Wrapper)it.next();
            Set samples = repository.getSamples(wrapper);
            MDLEvaluator evaluator = repository.getMDLEvaluator(wrapper);
            out.print(ind+ "<"+WRAPPER+" "+NAME+"=\""+wrapper.getName()+wrapper.getId()+"\"");
            out.print(" "+SOURCE+"=\""+getWrapperFile(wrapper).getName()+"\"");
            out.print(" "+DATASET+"=\""+getDataSetFile(wrapper).getName()+"\"");
            out.print(" "+NUMBEROFSAMPLES+"=\""+samples.size()+"\"");
            out.print(" "+COMPRESSRATIO+"=\""+formatter.format(evaluator.getCompressRatio())+"\"");
            out.print(" "+SCHEMADL+"=\""+evaluator.getSchemaDL()+"\"");
            out.print(" "+INSTANCESDL+"=\""+evaluator.getInstancesDL()+"\"");
            out.print(" "+SAMPLESDL+"=\""+evaluator.getSamplesDL()+"\"");
            out.println(">");
            ind.inc();
            int n = 0;
            Iterator samplesIt = samples.iterator();
            while (samplesIt.hasNext()) {
                Sample sample = (Sample)samplesIt.next();
                out.print(ind+ "<"+INSTANCE);
                out.print(" "+NAME+"=\""+sample.getName()+"\"");
                out.print(" "+SOURCE+"=\""+sample.getURL()+"\"");
                out.print(" "+SAMPLEDL+"=\""+sample.getDL()+"\"");
                out.print(" "+INSTANCEDL+"=\""+evaluator.getDLofInstance(n)+"\"");
                out.println("/>");
                n++;
            }
            ind.dec();
            out.println(ind+ "</"+WRAPPER+">");
        }
        ind.dec();
        out.println(ind+"</"+INDEX+">");
        out.close();
    }
    
    private void writeDataSetFiles() throws IOException, BindingException {
        Indenter ind = new Indenter(false);
        Iterator it = this.repository.getWrappers().iterator();
        while (it.hasNext()) {
            Wrapper wrapper = (Wrapper)it.next();
            log.fine("Saving data extracted by "+wrapper.getName()+wrapper.getId());
            Set samples   = repository.getSamples(wrapper);
            log.fine("There are "+samples.size()+" samples");
            
//            PrintWriter out = new PrintWriter(new FileWriter(getDataSetFile(wrapper)));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getDataSetFile(wrapper)),"utf-8"));
            out.println("<?xml version='1.0' encoding=\"UTF-8\"?>");
            out.println("<?xml-stylesheet href=\""+getRelativeDataSetStyleFile()+"\" type=\"text/xsl\"?>");
            out.println("<"+DATASET+" "+WRAPPEDBY+"=\""+wrapper.getName()+"\">");
            ind.inc();
            Set instances = repository.getInstances(wrapper);
            Iterator instancesIt = instances.iterator();
            while (instancesIt.hasNext()) {
                Instance instance = (Instance)instancesIt.next();
                new InstanceSerializer(instance,out,ind).encode();
            }
            ind.dec();
            out.println("</"+DATASET+">");
            out.close();
        }
    }
    
    
    
    
    private void writeWrapperFiles() throws IOException{
        Iterator it = this.repository.getWrappers().iterator();
        while (it.hasNext()) {
            Wrapper wrapper = (Wrapper)it.next();         
            wrapper.saveAs(getWrapperFile(wrapper));
        }
    }
    
}
