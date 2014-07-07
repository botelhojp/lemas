package tesma;

import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import moa.streams.ArffFileStream;
import weka.core.Instance;

public class MoaTest {

	public static void main(String[] args) {

		ArffFileStream stream = new ArffFileStream("/home/03397040477/feedback.arff", 7);

		Classifier learner = new HoeffdingTree();

		stream.prepareForUse();

		learner.setModelContext(stream.getHeader());
		learner.prepareForUse();

		int numberSamplesCorrect = 0;
		int numberSamples = 0;
		boolean isTesting = true;
		while (stream.hasMoreInstances()) {
			Instance trainInst = stream.nextInstance();
			if (isTesting) {
				if (learner.correctlyClassifies(trainInst)) {
					numberSamplesCorrect++;
				}
			}
			numberSamples++;
			learner.trainOnInstance(trainInst);
		}
		double accuracy = 100.0 * (double) numberSamplesCorrect / (double) numberSamples;
		System.out.println(numberSamples + " instancesp rocessed with " + accuracy + "% accuracy");
	}

}
