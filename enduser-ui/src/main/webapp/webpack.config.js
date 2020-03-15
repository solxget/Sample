var webpack = require('webpack');
var ProgressBarPlugin = require('progress-bar-webpack-plugin');

module.exports = {
    entry: {
        app: './app.tsx',
        asset: './asset-modules.js',
    },
    output: {
        filename: './bundle/[name].js'
    },
    resolve: {
        extensions: ['', '.webpack.js', '.web.js', '.js', '.ts', '.tsx', '.css', '.less', '.ttf']
    },
    plugins: [
    //  new webpack.optimize.UglifyJsPlugin(),
  /*       new webpack.DefinePlugin({
         	'process.env':{
         		'NODE_ENV': JSON.stringify('production')
         	 }
         }),
    		new webpack.optimize.UglifyJsPlugin({
    			compress:{
    				warnings: true
    			}
    		}),

        new ProgressBarPlugin(),
        new webpack.ProvidePlugin({
            'fetch': 'imports?this=>global!exports?global.fetch!whatwg-fetch'
        }). */
    ],
    module: {
        loaders: [
            { test: /\.ts$/, loader: 'ts' },
            { test: /\.tsx$/, loader: 'ts' },
            { test: /\.css$/, loader: 'style-loader!css-loader' },
            { test: /\.(woff|woff2)(\?v=\d+\.\d+\.\d+)?$/, loader: 'url?name=bundle/[name].[ext]&limit=10000&mimetype=application/font-woff'},
            { test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, loader: 'url?name=bundle/[name].[ext]&limit=10000&mimetype=application/octet-stream'},
            { test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, loader: 'file?name=bundle/[name].[ext]'},
            { test: /\.(jpg|png|ico|gif)$/, loader: 'file?name=bundle/[name].[ext]' },
            { test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, loader: 'url?name=bundle/[name].[ext]&limit=10000&mimetype=image/svg+xml'}
        ]
    }
}