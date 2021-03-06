(ns cloji.test.decoder
  (:use [cloji.decoder]
        [cloji.core]
        [cloji.test.helper]
        [clojure.test]))

(def ni (mobi-fixture "no_images.mobi"))
(def im (mobi-fixture "images2.mobi"))
(def hf (mobi-fixture "huff.mobi"))

(def no-images (decode-headers ni))
(def with-images (decode-headers im))
(def huff (decode-headers hf))

(deftest decode-headers-impl
  (testing "palmdoc header"
    (is (= "The_Adventur-herlock_Holmes" (:name no-images))))
  (testing "attributes"
    (is (= [] (:attributes no-images))))
  (testing "version"
    (is (= 0 (:version no-images))))
  (testing "creation date"
    (is (= 1303171212000 (.getTime (:creation-date no-images)))))
  (testing "modification date"
    (is (= 1303171212000 (.getTime (:modification-date no-images)))))
  (testing "backup date"
    (is (= nil (:backup-date no-images))))
  (testing "modification number"
    (is (= 0 (:modification-number no-images))))
  (testing "appinfo offset"
    (is (= 0 (:appinfo-offset no-images))))
  (testing "sortinfo offset"
    (is (= 0 (:sortinfo-offset no-images))))
  (testing "type"
    (is (= "BOOK" (:type no-images))))
  (testing "creator"
    (is (= "MOBI" (:creator no-images))))
  (testing "unique seed id"
    (is (= 379 (:seed-id no-images))))
  (testing "next record list id"
    (is (= 0 (:next-record-id no-images))))
  (testing "record count"
    (is (= 190 (:record-count no-images))))
  (testing "record-list"
    (let [first-record (first (:record-list no-images))
          second-record (nth (:record-list no-images) 1)]
      (is (= 1600 (:data-offset first-record)))
      (is (= [] (:attributes first-record)))
      (is (= 0 (:id first-record)))
      (is (= 10532 (:data-offset second-record)))
      (is (= [] (:attributes second-record)))
      (is (= 2 (:id second-record))))
    (is (= 190 (count (:record-list no-images)))))
  (testing "palmdoc header"
    (is (= 2 (:compression (:palmdoc-header no-images))))
    (is (= 730093 (:text-length (:palmdoc-header no-images))))
    (is (= 179 (:record-count (:palmdoc-header no-images))))
    (is (= 4096 (:record-size (:palmdoc-header no-images))))
    (is (= 0 (:current-position (:palmdoc-header no-images)))))
  (testing "mobi header"
    (is (= 232 (:header-length (:mobi-header no-images))))
    (is (= :mobi-book (:mobi-type (:mobi-header no-images))))
    (is (= :utf-8 (:encoding (:mobi-header no-images))))
    (is (= 1427074813 (:unique-id (:mobi-header no-images))))
    (is (= 6 (:file-version (:mobi-header no-images))))
    (is (= 0xFFFFFFFF (:ortographic-index (:mobi-header no-images))))
    (is (= 0xFFFFFFFF (:inflection-index (:mobi-header no-images))))
    (is (= 0xFFFFFFFF (:index-names (:mobi-header no-images))))
    (is (= 0xFFFFFFFF (:index-keys (:mobi-header no-images))))
    (is (= 0xFFFFFFFF (:extra-index-0 (:mobi-header no-images))))
    (is (= 0xFFFFFFFF (:extra-index-1 (:mobi-header no-images))))
    (is (= 0xFFFFFFFF (:extra-index-2 (:mobi-header no-images))))
    (is (= 0xFFFFFFFF (:extra-index-3 (:mobi-header no-images))))
    (is (= 0xFFFFFFFF (:extra-index-4 (:mobi-header no-images))))
    (is (= 0xFFFFFFFF (:extra-index-5 (:mobi-header no-images))))
    (is (= 181 (:first-nonbook-offset (:mobi-header no-images))))
    (is (= 704 (:full-name-offset (:mobi-header no-images))))
    (is (= 33 (:full-name-length (:mobi-header no-images))))
    (is (= 9 (:locale (:mobi-header no-images))))
    (is (= 0 (:input-language (:mobi-header no-images))))
    (is (= 0 (:output-language (:mobi-header no-images))))
    (is (= 6 (:min-version (:mobi-header no-images))))
    (is (= 313 (:first-image-offset (:mobi-header with-images))))
    (is (= 63 (:first-huff-rec (:mobi-header huff))))
    (is (= 3 (:huff-rec-count (:mobi-header huff))))
    (is (= 70 (:huff-table-offset (:mobi-header huff))))
    (is (= 1 (:huff-table-length (:mobi-header huff))))
    (is (= true (:exth-flags (:mobi-header huff))))
    (is (= 0xFFFFFFFF (:drm-offset (:mobi-header huff))))
    (is (= 0xFFFFFFFF (:drm-count (:mobi-header huff))))
    (is (= 0 (:drm-size (:mobi-header huff))))
    (is (= 0 (:drm-flags (:mobi-header huff)))))
  (testing "exth header"
    (is (= "EXTH" (:identifier (:exth-header no-images))))
    (is (= 15 (:record-count (:exth-header no-images))))
    (is (= 456 (:header-length (:exth-header no-images)))))
  (testing "exth header records"
    (let [records (:exth-records no-images)]
      (is (= "1999-03-01" (:publish-date records)))
      (is (= "Detective and mystery stories, English" (:subject records)))
      (is (= "Public domain in the USA." (:rights records)))
      (is (= "http://www.gutenberg.org/files/1661/1661-h/1661-h.htm" (:source records)))
      (is (= 54 (count (:font-signature records))))
      (is (= 201 (:creator records)))
      (is (= 1 (:creator-major records)))
      (is (= 2 (:creator-minor records)))
      (is (= 33307 (:creator-build records)))
      (is (= 0 (:cover-offset records)))
      (is (= false (:fake-cover records)))
      (is (= 1 (:thumb-offset records)))
      (is (= "Sir Arthur Conan Doyle" (:author records)))))
  (testing "extra flags"
    (is (= 3 (:extra-flags (:mobi-header no-images)))))
  (testing "full name"
    (is (= "The Adventures of Sherlock Holmes" (:full-name no-images)))))

(deftest palmdoc-decompression
  (testing "Literals and space compression"
    (is (= "<html><head><guide><reference title=" (subs (decode-record no-images ni 1) 0 36))))
  (testing "Distance pairs"
    (is (= "<html><head><guide><reference title=\"CONTENTS\" type=\"toc\"  filepos=0000001117 />" (subs (decode-record no-images ni 1) 0 80)))))

(deftest decode-record-impl
  (testing "decoding record n"
    (is (= "<html>" (apply str (take 6 (decode-record no-images ni 1))))))
  (testing "decoding a record with out of bounds errors"
    (is (= "after" (apply str (take 5 (decode-record no-images ni 8))))))
  (testing "reading the correct length given trailing entries"
    (is (= "and" (apply str (take-last 3 (decode-record no-images ni 8))))))
  (testing "decoding the last text record record"
    (is (= "old, " (apply str (take 5 (decode-record no-images ni 178)))))))

(comment (deftest decode-record-huff
  (testing "decoding the first record"
    (is (= "<html>" (apply str (take 6 (decode-record huff hf 1))))))
  (testing "reading with double entries"
    (is (= "<head>" (apply str (take 6 (drop 6 (decode-record huff hf 1)))))))
  (testing "recursive cdic unpacking"
    (is (= "<html><head><guide><reference title=\"Table of Contents\" type=\"toc\" filepos=0000006800 />"
           (apply str (take 88 (decode-record huff hf 1))))))))

(deftest decode-image-impl
  (testing "returns a BufferedImage"
    (is (instance? java.awt.image.BufferedImage (decode-image with-images im 0)))))

(deftest decode-all-images
  (testing "decoding a file with only a cover image"
    (is (= 1 (count (decode-images no-images ni)))))
  (testing "decoding a file with many images"
    (is (= 45 (count (decode-images with-images im))))))