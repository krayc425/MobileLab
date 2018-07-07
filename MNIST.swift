import Foundation
import TensorFlow

/// Returns the images tensor and labels tensor.
public func readMnist(imagesFile: String, labelsFile: String) -> (Tensor<Float>, Tensor<Int32>) {
    print("Reading data.")
    let imageData =
        try! Data(contentsOf: URL(fileURLWithPath: imagesFile)).dropFirst(16)
    let labelData =
        try! Data(contentsOf: URL(fileURLWithPath: labelsFile)).dropFirst(8)
    let images = imageData.map { Float($0) }
    let labels = labelData.map { Int32($0) }
    let rowCount = Int32(labels.count)
    let columnCount = Int32(images.count) / rowCount
    
    print("Constructing data tensors.")
    let imagesTensor = Tensor(shape: [rowCount, columnCount], scalars: images)
    let labelsTensor = Tensor(labels)
    return (imagesTensor.toDevice(), labelsTensor.toDevice())
}

func main() {
    // Get script directory.
    let currentDirectory =
        URL(fileURLWithPath: FileManager.default.currentDirectoryPath)
    let currentScriptPath = URL(fileURLWithPath: CommandLine.arguments[0],
                                relativeTo: currentDirectory)
    let scriptDirectory = currentScriptPath.appendingPathComponent("..").appendingPathComponent("MNIST_data")
    
    // Get training data.
    let imagesFile =
        scriptDirectory.appendingPathComponent("train-images.idx3-ubyte").path
    let labelsFile =
        scriptDirectory.appendingPathComponent("train-labels.idx1-ubyte").path
    
    let (images, numericLabels) = readMnist(imagesFile: imagesFile,
                                            labelsFile: labelsFile)
    let labels = Tensor<Float>(oneHotAtIndices: numericLabels, depth: 10)
    // FIXME: Defining batchSize as a scalar, or as a tensor as follows instead
    // of returning it from readMnist() crashes the compiler.
    let batchSize = Tensor<Float>(images.shapeTensor[0])
    
    // Hyper-parameters.
    let iterationCount: Int32 = 20
    let learningRate: Float = 0.2
    var loss = Float.infinity
    
    // Parameters.
    var w1 = Tensor<Float>(randomUniform: [784, 30])
    var b1 = Tensor<Float>(zeros: [1, 30])
    var w2 = Tensor<Float>(randomUniform: [30, 10])
    var b2 = Tensor<Float>(zeros: [1, 10])
    
    print("Begin Loop")

    // Training loop.
    var i: Int32 = 0
    repeat {
        // Forward pass.
        let z1 = images ⊗ w1 + b1
        let h1 = sigmoid(z1)
        let z2 = h1 ⊗ w2 + b2
        let predictions = sigmoid(z2)
        
        // Backward pass.
        let dz2 = (predictions - labels) / batchSize
        let dw2 = h1.transposed() ⊗ dz2
        let db2 = dz2.sum(squeezingAxes: 0)
        let dz1 = dz2.dot(w2.transposed()) * h1 * (1 - h1)
        let dw1 = images.transposed() ⊗ dz1
        let db1 = dz1.sum(squeezingAxes: 0)
        
        // Gradient descent.
        w1 -= dw1 * learningRate
        b1 -= db1 * learningRate
        w2 -= dw2 * learningRate
        b2 -= db2 * learningRate
        
        // Let m be the batch size, y be the target labels, and A be the
        // predictions.  The formula expressed in TF expression is:
        // 1/m * tf.reduce_sum(- y * tf.log(A) - (1-y) * tf.log(1-A))
        let part1 = -labels * log(predictions)
        let part2 = -(1 - labels) * log(1 - predictions)
        loss = (part1 + part2).sum() / batchSize.scalarized()
        
        print("Loss \(i)")
        
        // Update iteration count.
        i += 1
    } while i < iterationCount
    
    print("Total Loss \(loss)")
}

main()
