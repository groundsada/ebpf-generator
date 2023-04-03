import java.io.FileReader;
import java.net.URI;

import net.sourceforge.nrl.parser.NRLParser;
import net.sourceforge.nrl.parser.ast.IDeclaration;
import net.sourceforge.nrl.parser.ast.INRLAstNode;
import net.sourceforge.nrl.parser.ast.IRuleFile;
import net.sourceforge.nrl.parser.ast.action.IActionRuleDeclaration;
import net.sourceforge.nrl.parser.ast.constraints.IConstraintRuleDeclaration;
import net.sourceforge.nrl.parser.model.IPackage;
import net.sourceforge.nrl.parser.model.ModelCollection;
import net.sourceforge.nrl.parser.model.loader.StandaloneModelLoader;

public class Approach1 {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			StandaloneModelLoader y= new StandaloneModelLoader();
			//Schema file
			IPackage model = y.loadModel(new URI("src/main/resources/schema1.xsd"));
			NRLParser parser = new NRLParser();
			//Rules file
			IRuleFile ruleFile = parser.parse(new FileReader("src/main/resources/r1.nrl"));
			
			ModelCollection models = new ModelCollection();
			models.addModelPackage(model);
			
			parser.resolveModelReferences(ruleFile, models);
			//Resolve model errors
			if (parser.getErrors().size() > 0) {
				System.err.println(parser.getErrors().get(0));
				throw new Exception("Rule file has model errors.");
			}
			
			for (IDeclaration rule_dec : ruleFile.getDeclarations()) {
			
				INRLAstNode indicator = null;
								
				if (rule_dec instanceof IConstraintRuleDeclaration) {
					System.out.println(rule_dec.getId() + " is a Constraint Rule.");
					indicator = ((IConstraintRuleDeclaration) rule_dec).getConstraint();
				}
				else if (rule_dec instanceof IActionRuleDeclaration) {
					System.out.println(rule_dec.getId() + " is an Action Rule.");
					indicator = ((IActionRuleDeclaration) rule_dec).getAction();
				}
				
//				TypeFinder(indicator, rule_dec);
			
			
			}
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
