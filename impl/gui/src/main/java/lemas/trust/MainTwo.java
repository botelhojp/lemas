package lemas.trust;

import weka.core.Instance;
import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import moa.evaluation.LearningCurve;
import moa.evaluation.WindowClassificationPerformanceEvaluator;
import moa.streams.ArffFileStream;
import moa.tasks.EvaluatePrequential;

public class MainTwo {
	/**
	 * Main class for PreviousClassClassifier
	 * 
	 * @param args
	 */
	private static final String DEFAULT_INPUT_FILE = "Dropbox/Doutorado/feedback-777.arff";

	public static void main(String[] args) {

		// prepare classifier
		Classifier prevClassClasifier = new HoeffdingTree();

		// prepare input file for streaming evaluation
		String arffFilePath = null;
		if ((args == null) || (args.length < 1)) {
			arffFilePath = System.getenv("HOME") + "/" + DEFAULT_INPUT_FILE;
		} else {
			arffFilePath = args[0];
		}
		ArffFileStream electricityArff = null;
		try {
			electricityArff = new ArffFileStream(arffFilePath, -1);
			electricityArff.prepareForUse();
		} catch (Exception e) {
			System.out.println("Problem with loading arff file. Quit the program");
			System.exit(-1);
		}
		
		while(electricityArff.hasMoreInstances()){
			Instance e = electricityArff.nextInstance();
			System.out.println(prevClassClasifier.correctlyClassifies(e));
			prevClassClasifier.trainOnInstance(e);
//			System.out.println(le);
		}

		// prepare classification performance evaluator
		WindowClassificationPerformanceEvaluator windowClassEvaluator = new WindowClassificationPerformanceEvaluator();
		windowClassEvaluator.widthOption.setValue(1000);
		windowClassEvaluator.prepareForUse();

		// set EvaluatePrequential's parameter
		int maxInstances = 1000000;
		int timeLimit = -1;
		int sampleFrequencyOption = 1000;

		// do the learning and checking using evaluate-prequential technique
		EvaluatePrequential ep = new EvaluatePrequential();
		ep.instanceLimitOption.setValue(maxInstances);
		ep.learnerOption.setCurrentObject(prevClassClasifier);
		ep.streamOption.setCurrentObject(electricityArff);
		ep.sampleFrequencyOption.setValue(sampleFrequencyOption);
		ep.timeLimitOption.setValue(timeLimit);
		ep.evaluatorOption.setCurrentObject(windowClassEvaluator);
		ep.prepareForUse();

		// do the task and get the result
		LearningCurve le = (LearningCurve) ep.doTask();
		System.out.println("Evaluate prequential using HoeffdingTree");
		System.out.println("=====");
		System.out.println(le.getMeasurement(0, 1));
		System.out.println(le.getMeasurement(0, 2));
		System.out.println("=====");
		System.out.println(le);
		System.out.println("=====");
		
		
		
		
	}
}
