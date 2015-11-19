[:article {:class "post format-standard"}
 [:header {:class "post-header"}
  [:h1 {:class "post-title"}
   (if (= :post (:type metadata))
     (:title metadata)
     [:a {:href (:post-url metadata)} (:title metadata)])]
  [:p {:class "post-meta"}
   (:publish-date metadata)]]
 [:div {:class "post-content"} content]
 [:footer {:class "post-footer"}]]
