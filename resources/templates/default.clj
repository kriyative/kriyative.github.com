;;(doctype :html)
[:html {:lang "en"}
 [:head
  [:meta {:http-equiv "content-type" :content "text/html; charset=UTF-8"}]
  [:meta {:charset "UTF-8"}]
  [:meta {:name "description" :content (:description metadata)}]
  [:meta {:name "keywords" :content (:tags metadata)}]
  [:meta {:name "author" :content (:author-name config)}]
  [:link {:rel "icon" :href "/assets/favicon.ico" :type "image/x-icon"}]
  [:link {:rel "shortcut icon" :href "/assets/favicon.ico" :type "image/x-icon"}]
  [:link {:rel "stylesheet" :type "text/css" :href "/default.css"}]
  [:link {:rel "alternate" :type "application/rss+xml"
          :title (:site-title config) :href "/rss.xml"}]
  [:link {:href "http://fonts.googleapis.com/css?family=Snowburst+One"
          :rel "stylesheet"
          :type "text/css"}]

  (when (= (:type metadata) :post)
    [:link {:rel "canonical" 
	    :href (str (:site-url config) (:url metadata))}])

  [:title (or (:title metadata) (:site-title config))]]

 [:body
  [:header {:id "banner"}
   [:div {:class "sleeve"}
    [:hgroup {:id "nameplate"}
     [:h1 {:id "title"}
      [:a {:href "/"} (:site-title config)]]
     [:h2 {:id "description"}
      (:site-description config)]]
    [:nav {:id "toc"}
     (vec
      (concat
       [:ul {:id "menu"}]
       (map (fn [[label link]]
              [:li {:class "menu-item"}
               [:a {:href link} label]])
            [ ;; ["About" "/about"]
             ;; ["Contact" "/contact"]
             ["Archives" "/archives"]
             ["Tags" "/tags"]
             ["Subscribe" "/rss.xml"]])))]]]

  [:div {:id "main"}
   [:div {:class "sleeve"}
    [:div {:id "content"}
     content]

    [:aside {:id "sidebar"}
     [:section {:class "panel"}

      [:p
       [:img {:id "profile-pic"
              :src "http://www.gravatar.com/avatar/6dcf36ddd0aa73e04e125e3c10f6ff05.png"}]
       "Hi, I'm Ram Krishnan - a "
       [:a {:href "http://en.wikipedia.org/wiki/Lisp_(programming_language)"} "Lisp"]
       " programmer and software entrepreneur."]

      #_
      [:p "This is a blog about Lisp and life in general. Apart from
       programming computers, I enjoy building gravity defying Lego
       towers with my children, and playing the violin - sometimes all
       at the same time."]]

     [:section {:class "panel"}
      [:h1 "Links"]
      [:ul
       [:li [:a {:href "https://github.com/kriyative"} "Github"]]
       [:li [:a {:href "http://juxt.io"} "Juxt.IO"]]]

      [:p "I sometimes post on "
       [:a {:href "http://twitter.com/funcall"} "twitter"] "."]]

     #_
     [:section {:class "panel"}
      [:form {:id "search" :action "https://duckduckgo.com/" :method "get"}
       [:input {:type "hidden" :name "sites" :value "ramkrishnan.net"}]
       [:label {:for "search-field"} "Search for:"]
       [:input {:type "text" :name "q" :id "search-field" :placeholder "Search"}]]]
     [:section {:class "panel"}
      [:h1 "Recent posts"]
      (vec
       (concat
        [:ul]
        (map (fn [f]
               (let [url (static.core/post-url f)
                     [metadata _] (static.io/read-doc f)
                     date (static.core/publish-date f)]
                 [:li [:a {:href url} (:title metadata)]]))
             (take 10 (reverse (static.io/list-files :posts))))))]]]]
  [:footer {:id "addendum"}
   [:div {:class "sleeve"}
    [:p {:id "copyright"} "Copyright © 2012–2013 Ram Krishnan"]]]

  (when-let [disqus-url (:disqus-forum-url config)]
    [:div {:id "disqus"} 
     (when (= (:type metadata) :post)
       [:div
        [:div {:id "disqus_thread"}]
        [:script {:type "text/javascript" :src (format "%s/embed.js" disqus-url)}]
        [:noscript
         [:a {:href (format "%s/?url=ref" disqus-url)} "View the discussion thread"]]
        [:a {:href "http://disqus.com" :class "dsq-brlink"}
         "blog comments powered by "
         [:span {:class "logo-disqus"} "Disqus"]]])])

  (when-let [goog-id (:google-analytics-id config)]
    [:script {:type "text/javascript"}
     (format
      "var _gaq = _gaq || [];
       _gaq.push(['_setAccount', '%s']);
       _gaq.push(['_trackPageview']);
      (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(ga);
       })();"
      goog-id)])]]

#_
(comment

  [:h1 [:a {:href "/"} (:site-title config)]]
  [:ul {:class "pages"}
   [:li [:a {:href "/" :class "page"} "Home"]]
   [:li [:a {:href "/tags/" :class "page"} "Tags"]]
   [:li [:a {:href "/contact.html" :class "page" :rel "author"} "About"]]]


  [:form {:method "get" :action "http://www.google.com/search" :id "searchform"}
   [:div
    [:input {:type "text" :name "q" :class "box" :id "s"}]
    [:input {:type "hidden" :name "sitesearch" :value (:site-url config)}]]]

  [:div {:id "post"}
   [:h1 (:title metadata)]
   [:p {:class "publish_date"}  
    (:publish-date metadata)]]

  (reduce (fn [h v]
            (conj h [:a {:href (str "/tags/#" v)} (str v " ")]))
          [:div {:class "post-tags"} "Tags: "] 
          (.split (:tags metadata) " ")))
