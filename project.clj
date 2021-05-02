(defproject looney "0.1.0-SNAPSHOT"
  :plugins [[lein-less "1.7.5"]]

  :clean-targets ^{:protect false} ["target" "resources/public/js/compiled"]

  :less {:source-paths ["less"]
         :target-path  "resources/public/css"})
