package lemas;

import java.text.ParseException;

import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import moa.core.InstancesHeader;
import moa.streams.ArffFileStream;
import weka.core.DenseInstance;
import weka.core.Instance;

public class MoaTest {

	public static void main(String[] args) {

		ArffFileStream stream = new ArffFileStream("C:\\Users\\vanderson\\feedback.arff", 7);

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

	public static Instance makeInstance(InstancesHeader instancesHeader) {
		try {
			Instance inst = new DenseInstance(instancesHeader.numAttributes());
			inst.setDataset(instancesHeader);

			inst.setValue(instancesHeader.attribute(0), 53041511);
			inst.setValue(instancesHeader.attribute(1), 45323847);

			inst.setValue(instancesHeader.attribute(2), instancesHeader.attribute(2).parseDate("01/01/2013"));

			inst.setValue(instancesHeader.attribute(3), "boa");
			inst.setValue(instancesHeader.attribute(4), "vamos lá");
			inst.setValue(instancesHeader.attribute(5), 10.0);
			inst.setValue(instancesHeader.attribute(6), "pos");

			return inst;
		} catch (ParseException e) {
			throw new RuntimeException("");
		}
	}

}
