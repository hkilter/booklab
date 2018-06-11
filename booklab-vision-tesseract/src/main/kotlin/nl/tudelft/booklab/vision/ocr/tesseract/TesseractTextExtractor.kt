/*
 * Copyright 2018 The BookLab Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.tudelft.booklab.vision.ocr.tesseract

import nl.tudelft.booklab.vision.ocr.TextExtractor
import org.opencv.core.Mat

/**
 * A [TextExtractor] using the Tesseract library for OCR purposes.
 *
 * @param tesseract The Tesseract API wrapper to use for this text extractor implementation.
 */
class TesseractTextExtractor(private val tesseract: Tesseract) : TextExtractor {
    override fun extract(mat: Mat): List<String> = listOf(tesseract.extract(mat))
    override fun batch(matrices: List<Mat>): List<String> = matrices.map { mat -> tesseract.extract(mat) }
}