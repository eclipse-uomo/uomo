package org.eclipse.uomo.ucum.impl;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.ohf.h3et.mif.core.MIFDocument;
import org.eclipse.ohf.h3et.mif.core.MIFDocumentKind;
import org.eclipse.ohf.h3et.mif.core.base.MIFBaseObject;
import org.eclipse.ohf.h3et.mif.core.base.MIFXhtmlContent;
import org.eclipse.ohf.h3et.mif.core.model.MIFCascadableAnnotation;
import org.eclipse.ohf.h3et.mif.core.model.MIFCode;
import org.eclipse.ohf.h3et.mif.core.model.MIFCodeSystem;
import org.eclipse.ohf.h3et.mif.core.model.MIFCodeSystemVersion;
import org.eclipse.ohf.h3et.mif.core.model.MIFComplexMarkupWithLanguage;
import org.eclipse.ohf.h3et.mif.core.model.MIFConcept;
import org.eclipse.ohf.h3et.mif.core.model.MIFConceptAnnotations;
import org.eclipse.ohf.h3et.mif.core.model.MIFConceptProperty;
import org.eclipse.ohf.h3et.mif.core.model.MIFConceptPropertyList;
import org.eclipse.ohf.h3et.mif.core.model.MIFConceptPropertyTypeKind;
import org.eclipse.ohf.h3et.mif.core.model.MIFDocumentation;
import org.eclipse.ohf.h3et.mif.core.model.MIFGlobalVocabularyModel;
import org.eclipse.ohf.h3et.mif.core.model.MIFPrintName;
import org.eclipse.ohf.h3et.mif.core.model.MIFSupportedConceptProperty;
import org.eclipse.uomo.core.UOMoException;
import org.eclipse.uomo.ucum.Generator;
import org.eclipse.uomo.ucum.model.BaseUnit;
import org.eclipse.uomo.ucum.model.DefinedUnit;
import org.eclipse.uomo.ucum.model.Prefix;
import org.eclipse.uomo.ucum.model.UcumModel;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;


/**
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 *
 */
public class MIFGeneratorImpl implements Generator<UcumModel, MIFDocument> {
	private static final DateFormat MIF_DATE_SHORT = new SimpleDateFormat("yyyy-MM-dd");
	public MIFDocument generate(UcumModel model) throws UOMoException {
		MIFDocument mif = new MIFDocument();
		mif.setKind(MIFDocumentKind.VOCABULARY_MODEL);
		MIFGlobalVocabularyModel vocab = mif.getVocabularyModel();
		MIFCodeSystem sys = new MIFCodeSystem(vocab);
		vocab.getCodeSystemList().add(sys);
		MIFCodeSystemVersion ver = new MIFCodeSystemVersion(sys);
		sys.getReleasedVersionList().add(ver);
		
		sys.setName(model.getName());
		sys.setCodeSystemId(UcumEssenceService.UCUM_OID);
		ver.setReleaseDate(MIF_DATE_SHORT.format(model.getRevisionDate()));
		ver.setPublisherVersionId(model.getVersion());  
		addSupportedProperty(ver, ver.getSupportedConceptPropertyList(), "kind", "whether this is a prefix, a base unit, or a unit", MIFConceptPropertyTypeKind.ENUMERATION);
		addSupportedProperty(ver, ver.getSupportedConceptPropertyList(), "value", "for a prefix or unit, the value. (for units, there is also a unit for the value)", MIFConceptPropertyTypeKind.REAL);
		addSupportedProperty(ver, ver.getSupportedConceptPropertyList(), "dimension", "for a base unit or unit, the dimension", MIFConceptPropertyTypeKind.ENUMERATION);
		addSupportedProperty(ver, ver.getSupportedConceptPropertyList(), "property", "for a base unit, the property", MIFConceptPropertyTypeKind.TOKEN);
		addSupportedProperty(ver, ver.getSupportedConceptPropertyList(), "unit", "for a unit, the unit in which the value is represented", MIFConceptPropertyTypeKind.STRING);
		
		definePrefixes(model, ver);
		defineBaseUnits(model, ver);
		defineUnits(model, ver);
		return mif;
	}
	private void addSupportedProperty(MIFBaseObject owner, List<MIFSupportedConceptProperty> list, String name, String doco, MIFConceptPropertyTypeKind kind) {
		MIFSupportedConceptProperty prop = new MIFSupportedConceptProperty(owner);
		prop.setPropertyName(name);
		prop.setIsMandatoryIndicator(false);
		prop.setType(kind);
		MIFXhtmlContent d = new MIFXhtmlContent(prop);
		prop.setDescription(d);
//		d.setContent(doco.getBytes());
		d.setContent(("<html>"+doco+"</html>").getBytes());
		list.add(prop);
		
	}

	private void definePrefixes(UcumModel model, MIFCodeSystemVersion ver) {
		for (Prefix prefix : model.getPrefixes()) {
			// add concept
			MIFConcept c = new MIFConcept(ver);
			ver.getConceptList().add(c);
			c.setIsSelectable(false); // todo: is this true?
			c.setAnnotations(new MIFConceptAnnotations(c));
			c.getAnnotations().setDocumentation(new MIFDocumentation(c.getAnnotations()));
			c.getAnnotations().getDocumentation().setDescription(makeAnnotation(c.getAnnotations().getDocumentation(), getHtml(prefix.getValue())));
			// give it two print name - name and printSymbol
			MIFPrintName p = new MIFPrintName(c);
			c.getPrintNameList().add(p);
			p.setText(prefix.getPrintSymbol());
			for (String name : prefix.getNames()) {
				p = new MIFPrintName(c);
				c.getPrintNameList().add(p);
				p.setText(name);
			}

			// two codes: Case Sensitive and Case Insensitive
			MIFCode code = new MIFCode(c);
			c.getCodeList().add(code);
			code.setCode(prefix.getCode());
			
			addProperty(c, c.getConceptPropertyList(), "value", ((BigDecimal)prefix.getValue()).toEngineeringString());
			addProperty(c, c.getConceptPropertyList(), "kind", "prefix");
		}
		
	}

	private String getHtml(Number value) {
		String rep = ((BigDecimal)value).toEngineeringString();
		if (rep.charAt(1) == 'E')
			return "1 x 10<sup>"+rep.substring(2)+"</sup>";
		else
			return rep;
	}

	private MIFCascadableAnnotation makeAnnotation(MIFBaseObject owner, String source) {
		MIFCascadableAnnotation a = new MIFCascadableAnnotation(owner);
		MIFComplexMarkupWithLanguage m = new MIFComplexMarkupWithLanguage(a);
		a.getTextList().add(m);
		m.setContent(("<html>"+source+"</html>").getBytes());
		return a;
	}

	private void addProperty(MIFBaseObject owner, MIFConceptPropertyList list, String name, String value) {
		MIFConceptProperty prop = new MIFConceptProperty(owner);
		prop.setName(name);
		prop.setValue(value);
		list.add(prop);
	}

	private void defineBaseUnits(UcumModel model, MIFCodeSystemVersion ver) {
		for (BaseUnit base : model.getBaseUnits()) {
			// add concept
			MIFConcept c = new MIFConcept(ver);
			ver.getConceptList().add(c);
			c.setIsSelectable(true); 

			// give it two print name - name and printSymbol
			MIFPrintName p = new MIFPrintName(c);
			c.getPrintNameList().add(p);
			p.setText(base.getPrintSymbol());
			for (String name : base.getNames()) {
				p = new MIFPrintName(c);
				c.getPrintNameList().add(p);
				p.setText(name);
			}

			// two codes: Case Sensitive and Case Insensitive
			MIFCode code = new MIFCode(c);
			c.getCodeList().add(code);
			code.setCode(base.getCode());
			
			addProperty(c, c.getConceptPropertyList(), "kind", "base-unit");
			addProperty(c, c.getConceptPropertyList(), "property", base.getProperty());
			addProperty(c, c.getConceptPropertyList(), "dimension", String.valueOf(base.getDim()));
		}
	}

	private void defineUnits(UcumModel model, MIFCodeSystemVersion ver) {
		for (DefinedUnit unit : model.getDefinedUnits()) {
			// add concept
			MIFConcept c = new MIFConcept(ver);
			ver.getConceptList().add(c);
			c.setIsSelectable(true); 

			// give it two print name - name and printSymbol
			MIFPrintName p;
			for (String name : unit.getNames()) {
				p= new MIFPrintName(c);
				c.getPrintNameList().add(p);
				p.setText(name);
			}
			if (unit.getPrintSymbol() != null) {
				p = new MIFPrintName(c);
				c.getPrintNameList().add(p);
				p.setText(unit.getPrintSymbol());
			}

			// two codes: Case Sensitive and Case Insensitive
			MIFCode code = new MIFCode(c);
			c.getCodeList().add(code);
			code.setCode(unit.getCode());
			
			addProperty(c, c.getConceptPropertyList(), "kind", "unit");
			addProperty(c, c.getConceptPropertyList(), "property", unit.getProperty());
			if (unit.getValue() != null) {
				if (unit.getValue().value() != null)
					addProperty(c, c.getConceptPropertyList(), "value", ((BigDecimal)unit.getValue().value()).toEngineeringString());
				addProperty(c, c.getConceptPropertyList(), "unit", unit.getValue().getUnit());
			}
		}
	}

}
