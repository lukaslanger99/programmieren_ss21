package de.ostfalia.prog.s21ss.s1.test;

import static org.junit.Assert.assertFalse;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import de.ostfalia.junit.annotations.AfterMethod;
import de.ostfalia.junit.annotations.TestDescription;
import de.ostfalia.junit.base.IMessengerRules;
import de.ostfalia.junit.base.ITraceRules;
import de.ostfalia.junit.conditional.PassTrace;
import de.ostfalia.junit.conditional.Reflect;
import de.ostfalia.junit.rules.MessengerRule;
import de.ostfalia.junit.rules.RuleControl;
import de.ostfalia.junit.rules.TraceRule;
import de.ostfalia.junit.runner.TopologicalSortRunner;
import de.ostfalia.prog.s21ss.s1.model.Pyramide;

@RunWith(TopologicalSortRunner.class)
public class TestPyramide {

	public RuleControl opt = RuleControl.NONE;
	public IMessengerRules messenger = MessengerRule.newInstance(opt);	
	public ITraceRules trace = TraceRule.newInstance(opt);	

	@Rule
	public TestRule chain = RuleChain
							.outerRule(trace)	
							.around(messenger);
	
	@Rule
    public TestRule timeout = new DisableOnDebug(
                              new Timeout(1000, TimeUnit.MILLISECONDS));
	
	@Test
	@TestDescription("Ueberpruefen des Konstruktors der Pyramidenklasse.")	
	public void testConstructor() {
		trace.add(PassTrace.ifEquals("Es duerfen keine oeffentliche Konstruktoren existieren.",
				0, Reflect.construktors(Pyramide.class).length));
		trace.separator();
		assertFalse("Klasse Pyramide besitzt oeffentliche(n) Konstruktor(en).", 
				trace.hasOccurrences());		
	}

	@Test
	@TestDescription("Ueberpruefen der Methode \"getInstance()\" der Pyramidenklasse")	
	@AfterMethod("testConstructor")
	public void testGetInstance() {	
		String name = "\"getInstance()\"";
		trace.add(PassTrace.ifEquals("Pyramide muss die Methode %s besitzen.", 
				true, Reflect.hasMethod(Pyramide.class, "getInstance", null), name));
		
		trace.add(PassTrace.ifEquals("Rueckgabetyp von %s muss eine Instanz von Pyramide sein.", 
				true, Reflect.hasMethod(Pyramide.class, "getInstance", Pyramide.class), name));
		
		
		int modifiers = Reflect.getModifiers(Pyramide.class, "getInstance");
		trace.add(PassTrace.ifEquals("Methode %s muss \"static\" deklariert sein.",
				true, Modifier.isStatic(modifiers), name));
		
		trace.separator();
		assertFalse("Die Methode \"getInstance()\" der Pyramidenklasse entspricht nicht den Vorgaben.", 
				trace.hasOccurrences());
		
		Method method = Reflect.getMethod(Pyramide.class, "getInstance", Pyramide.class);
		Pyramide pyramide = Reflect.call(Pyramide.class, method);
		trace.add(PassTrace.ifNotNull("Die Methode \"getInstance()\" darf nicht null liefern.", pyramide));
		
		trace.separator();
		assertFalse("Die Methode \"getInstance()\" liefert null.", trace.hasOccurrences());
	}

	@Test
	@TestDescription("Ueberpruefen der Methode \"initialisiere(String...)\" der Pyramidenklasse")	
	@AfterMethod("testGetInstance")
	public void testInitialisiere() {			
		String name = "\"initialisiere(String...)\"";
		trace.add(PassTrace.ifEquals("Pyramide muss die Methode %s besitzen.", 
				true, Reflect.hasMethod(Pyramide.class, "initialisiere", null, String[].class), name));
		
		trace.add(PassTrace.ifEquals("Rueckgabetyp von %s muss void sein.", 
				true, Reflect.hasMethod(Pyramide.class, "initialisiere", void.class, String[].class), name));
				
		int modifiers = Reflect.getModifiers(Pyramide.class, "initialisiere", String[].class);
		trace.add(PassTrace.ifEquals("Methode %s darf nicht \"static\" deklariert sein.",
				false, Modifier.isStatic(modifiers), name));
		
		trace.separator();
		assertFalse("Die Methode \"initialisiere(String)\" der Pyramidenklasse entspricht nicht den Vorgaben.", 
				trace.hasOccurrences());
	}
}
