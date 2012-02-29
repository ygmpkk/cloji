(ns cloji.test.encoder
  (:use [clojure.test]
        [cloji.test.helper])
  (:require [cloji.encoder :as encoder]
            [cloji.decoder :as decoder]
            [cloji.core :as core]))

(def ni (mobi-fixture "no_images.mobi"))
(def no-images (decoder/decode-headers ni))

(deftest encode-headers-imp
  (let [headers (encoder/encode-headers no-images)]
    (testing "encoding the pdb document name"
      (is (= (subvec headers 0 31) [0x54 0x68 0x65 0x5F 0x41 0x64 0x76 0x65 0x6E 0x74 0x75 0x72 0x2D 0x68 0x65 0x72 0x6C 0x6F 0x63 0x6B 0x5F 0x48 0x6F 0x6C 0x6D 0x65 0x73 00 00 00 00])))
    (testing "encoding the palmdoc attributes"
      (is (= (subvec headers 32 34) [0 0])))
    (testing "encoding the version number"
      (is (= (subvec headers 34 36) [0 0])))
    (testing "encoding the creation date, modification date, backup date"
      (is (= (subvec headers 36 40) [0x4D 0xAC 0xD0 0x8C]))
      (is (= (subvec headers 40 44) [0x4D 0xAC 0xD0 0x8C]))
      (is (= (subvec headers 44 48) [0 0 0 0])))
    (testing "encoding the modification number"
      (is (= (subvec headers 48 52) [0 0 0 0])))
    (testing "encoding the appinfo offset"
      (is (= (subvec headers 52 56) [0 0 0 0])))
    (testing "ecnoding the sortinfo offset"
      (is (= (subvec headers 56 60) [0 0 0 0])))))
    